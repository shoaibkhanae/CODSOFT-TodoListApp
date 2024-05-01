package com.example.todo

import android.app.Application
import com.example.todo.data.TodoDatabase
import com.example.todo.repository.ItemRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application()