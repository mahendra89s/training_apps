package com.example.neosoftstore.adapters

interface CartListener {
    fun deleteItem(productId : Int)
    fun editItem(productId: Int,quantity: Int)
}