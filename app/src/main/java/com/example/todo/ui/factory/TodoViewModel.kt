package com.example.todo.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Item
import com.example.todo.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: ItemRepository): ViewModel() {
    val allItems = repository.allWords.asLiveData()

    fun insert(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(item)
        }
    }
    fun delete(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
        }
    }
    fun update(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(item)
        }
    }
}

class TodoViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}