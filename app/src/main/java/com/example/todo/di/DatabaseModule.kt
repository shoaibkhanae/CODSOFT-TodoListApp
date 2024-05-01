package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.data.ItemDao
import com.example.todo.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideItemDao(database: TodoDatabase): ItemDao {
        return database.getDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }
}