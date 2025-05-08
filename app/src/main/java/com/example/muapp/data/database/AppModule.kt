package com.example.muapp.data.database

import android.content.Context
import androidx.room.Room
import com.example.muapp.data.dao.TodoDAO
import com.example.muapp.data.repository.TodoRepository
import com.example.muapp.data.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDAO): TodoRepository{
        return TodoRepositoryImpl(dao)
    }
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context) :
            AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "MUAPP_db"
        ).build()
    }
    @Provides
    fun provideTodoDAO(database: AppDatabase): TodoDAO{
        return database.todoDao()
    }
}