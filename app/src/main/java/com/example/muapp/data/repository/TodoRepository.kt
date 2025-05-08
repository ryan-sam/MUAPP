package com.example.muapp.data.repository

import com.example.muapp.data.dao.TodoDAO
import com.example.muapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<List<TodoItem>>
    suspend fun getTodoById(id: Int): TodoItem?
    suspend fun insertTodo(todo: TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
}
class TodoRepositoryImpl(private val todoDAO: TodoDAO) :
        TodoRepository{
    override fun getAllTodos(): Flow<List<TodoItem>> {
       return todoDAO.getAllTodos()
    }

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

}