package com.example.neosoftstore.models

data class ListCartResponseModel(
    val count: Int,
    val `data`: List<ListCartData>,
    val status: Int,
    val total: Double
)
data class ListCartData(
    val id: Int,
    val product: Product,
    val product_id: Int,
    val quantity: Int
)