package com.example.muapp.presentation.screens.dashboard
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muapp.data.model.TodoItem
import com.example.muapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository : TodoRepository
) : ViewModel(){
    // mock data // hard coded

    private val mockTodo = listOf(
        TodoItem(
            id = 1,
            title = "Buy Groceries",
            description = "Milk, Eggs, and Bread",
            imageUri = null,
            tasker = "Ryan",
            isCompleted = false
        ),
        TodoItem(
            id = 2,
            title = "Travel",
            description = "Travel back home at @3PM Saturday",
            imageUri = null,
            tasker = "Me",
            isCompleted = false
        ) ,
         TodoItem(
            id = 3,
            title = "Travel",
            description = "Travel back home at @3PM Saturday",
            imageUri = null,
            tasker = "Me",
            isCompleted = true
        )
    )
    // create a stateflow - making a data visible to everyone
    //1. keep mutable stateflow private
   // getting the data from repository layer
    val todos: StateFlow<List<TodoItem>> = repository.getAllTodos()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()

    )
    val firebaseTodos : StateFlow<List<TodoItem>> =
        repository.fetchtodosFromFirebase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue =  emptyList()
        )
    fun deleteTodoFromFirebase(todo: TodoItem){
        viewModelScope.launch {
            repository.deleteTodoFirebase(todo)
        }
    } fun updateTodoFromFirebase(todo: TodoItem){
        viewModelScope.launch {
            repository.updateTodoFirebase(todo)
        }
    }
    // functions working on the data being worked on
    fun toogleTodoCompletion(todoId: Int){
        // 1. making refrence to all todos
        //2. Reassign the todos value according to new state for isCompleted
        // 3. this step will allow new renders on a MUAPPitem change
        // 4. it references a single MUAPPitem
//        _todos.value = _todos.value.map{todo ->
//            if (todo.id == todoId) todo.copy(isCompleted = !todo.isCompleted)
//            else todo
//        }
        // observing changes in the repository to update the viewmodel
        viewModelScope.launch {
            val todo = repository.getTodoById(todoId) ?: return@launch
            val updateTodo = todo.copy(isCompleted = !todo.isCompleted)
            repository.updateTodo(updateTodo)
        }

    }

    fun addTodo(
        title: String, description: String, tasker: String, imageUri: Uri?
    ){
        viewModelScope.launch{
            // we create new item
            var imageUrl: String? = null
            if (imageUri != null ){
                imageUrl = repository.uploadImagetoFirebase(imageUri)
            }
            val newTodo = TodoItem(
                id = 0,
                title = title,
                description = description,
                imageUri = imageUrl,
                tasker = tasker,
                isCompleted = false
            )
            repository.insertTodo(newTodo)
            // firebase insert
            repository.uploadToFirebase(newTodo)
        }
    }
}