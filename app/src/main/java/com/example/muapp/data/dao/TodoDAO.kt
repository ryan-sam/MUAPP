package com.example.muapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.muapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

// Annotate the interface as DAO interface from room lib.
@Dao
interface TodoDAO {
    // methods for the crud operations
    // annotations from android room
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<TodoItem>>
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoItem
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoItem)

    @Update
    suspend fun updateTodo(todo: TodoItem)

    @Delete
    suspend fun deleteTodo(todo: TodoItem)

}