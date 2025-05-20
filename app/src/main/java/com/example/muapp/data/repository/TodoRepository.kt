package com.example.muapp.data.repository

import android.net.Uri
import com.example.muapp.data.dao.TodoDAO
import com.example.muapp.data.model.TodoItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

interface TodoRepository {
    fun getAllTodos(): Flow<List<TodoItem>>
    fun fetchtodosFromFirebase():Flow<List<TodoItem>>
    suspend fun getTodoById(id: Int): TodoItem?
    suspend fun insertTodo(todo: TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
    suspend fun uploadToFirebase(todo: TodoItem)
    suspend fun uploadImagetoFirebase(imageUri: Uri?): String
    suspend fun updateTodoFirebase(todo: TodoItem)
    suspend fun deleteTodoFirebase(todo: TodoItem)

}
class TodoRepositoryImpl(private val todoDAO: TodoDAO) :
        TodoRepository{
    override fun getAllTodos(): Flow<List<TodoItem>> {
       return todoDAO.getAllTodos()
    }

    override fun fetchtodosFromFirebase(): Flow<List<TodoItem>>
    = callbackFlow{
        val dbref = FirebaseDatabase.getInstance().reference
            .child("todos")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val todos = mutableListOf<TodoItem>()
                for (child in snapshot.children){
                    val todo = child.getValue(TodoItem::class.java)
                    todo?.let { todos.add(it) }
                }
                // corountines flow : trysends handles any exception
                // encountered in process
                trySend(todos).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                 close(error.toException())
            }

        }

// add listener to db reference
        dbref.addValueEventListener(listener)
        // if app is not launched close connection
        awaitClose{dbref.removeEventListener(listener)}
    }

// .get listens for data once . static  implementation for fetching data
//    override fun fetchtodosFromFirebase():Flow<List<TodoItem>>
//            = flow{
//
//        val dbref = FirebaseDatabase.getInstance().reference.child("todos")
//        // suspend , reads the data currently#
//        val snapshot = dbref.get().await()
//        val todos = mutableListOf<TodoItem>()
//        // now populate above list ref, with the snapshot details
//        for (child in snapshot.children){
//            val todo = child.getValue(TodoItem::class.java)
//            todo?.let { todos.add(it) }
//        }
//        // expose the items to viewmodel
//    }


    override suspend fun getTodoById(id: Int): TodoItem? {
        return todoDAO.getTodoById(id)
    }

    override suspend fun insertTodo(todo: TodoItem) {
        return todoDAO.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoItem) {
        return todoDAO.deleteTodo(todo)
    }

    override suspend fun updateTodo(todo: TodoItem) {
        return todoDAO.updateTodo(todo)
    }
    override suspend fun uploadToFirebase(todo: TodoItem) {
        // firebase db initialization
        val database  = FirebaseDatabase.getInstance()
            .reference
        // we target our db by name
        val newTodoRef = database.child("todos")
            .push()
        // generate and capture unique id from firebase
        val firebaseId = newTodoRef.key?: return
        val todoWithId = todo.copy(firebase_id = firebaseId)
        // then we insert the data to realtime db
        newTodoRef.setValue(todoWithId)
    }

    // suspend function
    override suspend fun uploadImagetoFirebase(
        imageUri: Uri?) : String {
        // storage bucket reference ; initialize
        val storageRef = FirebaseStorage.getInstance()
            .reference
        val imageRef = storageRef.child(
            "todo_images/${UUID.randomUUID()}.jpg")
        // push image to the folder above
        if (imageUri != null) {
            imageRef.putFile(imageUri).await()
        }
        return imageRef.downloadUrl.await().toString()
    }



    override suspend fun updateTodoFirebase(todo: TodoItem) {
        val firebaseId = todo.firebase_id
        if (firebaseId.isNotEmpty()) {
            val dbref = FirebaseDatabase.getInstance().reference.child("todos").child(firebaseId)
            dbref.removeValue().await()
            // snapshot , -> filter for the record

        } else {
            throw IllegalArgumentException("Firebase id is empty")
        }


    }
    override suspend fun deleteTodoFirebase(todo: TodoItem) {
        val firebaseId = todo.firebase_id
        if (firebaseId.isNotEmpty()){
            val dbref = FirebaseDatabase.getInstance().reference.child("todos").child(firebaseId)
            dbref.setValue(todo).await()
        } else {
            throw IllegalArgumentException("Firebase id is empty")
        }

    }


}