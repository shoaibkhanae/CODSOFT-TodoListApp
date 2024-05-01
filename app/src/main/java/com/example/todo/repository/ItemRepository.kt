package com.example.todo.repository

import com.example.todo.data.Item
import com.example.todo.data.ItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

    val allWords: Flow<List<Item>> = itemDao.getAllItems()


    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }

    suspend fun update(item: Item) {
        itemDao.update(item)
    }

}