package com.example.muapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//to define data model in android we annotate with extension activity
// Entity is part room persistence library , which marks my class as persistence entity i.e. database table
// each property defined i this class wil be a column for our local sqlite table
@Entity(tableName = "todos")
// define the data class by adding data keyword
data class TodoItem (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firebase_id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUri: String? = "",
    val tasker: String= "",
    val createdAt: Long = System.currentTimeMillis(), // captures time
    val isCompleted: Boolean = false
)


































