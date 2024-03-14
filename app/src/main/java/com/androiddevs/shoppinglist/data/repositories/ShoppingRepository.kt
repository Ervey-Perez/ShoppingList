package com.androiddevs.shoppinglist.data.repositories

import com.androiddevs.shoppinglist.data.db.ShoppingDao
import com.androiddevs.shoppinglist.data.db.ShoppingDatabase
import com.androiddevs.shoppinglist.data.db.entities.ShoppingItem

class ShoppingRepository(
    private val db: ShoppingDatabase
) {
    suspend fun upsert(item: ShoppingItem) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()
}