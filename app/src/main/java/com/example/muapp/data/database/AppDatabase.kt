package com.example.muapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muapp.data.dao.TodoDAO
import com.example.muapp.data.model.TodoItem

// Add annotation database to mark tis class as database migration layer
@Database(
    entities = [TodoItem::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // define an abstract fun for the database interface
        abstract fun todoDao() : TodoDAO
        // add seed data ....
}