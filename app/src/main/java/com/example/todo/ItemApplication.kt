package com.example.todo

import android.app.Application
import com.example.todo.data.TodoDatabase
import com.example.todo.repository.ItemRepository

class ItemApplication: Application() {
    private val database by lazy { TodoDatabase.getDatabase(this) }
    val repository by lazy { ItemRepository(database.getDao()) }
}