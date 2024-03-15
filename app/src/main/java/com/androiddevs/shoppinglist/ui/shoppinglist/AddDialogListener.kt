package com.androiddevs.shoppinglist.ui.shoppinglist

import com.androiddevs.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}