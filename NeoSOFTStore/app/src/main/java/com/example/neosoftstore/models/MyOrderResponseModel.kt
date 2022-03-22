package com.example.neosoftstore.models

data class MyOrderResponseModel(
    val data: List<MyOrderData>,
    val message: String,
    val status: Int,
    val user_msg: String
)

data class MyOrderData(
    val cost: Int,
    val created: String,
    val id: Int
)