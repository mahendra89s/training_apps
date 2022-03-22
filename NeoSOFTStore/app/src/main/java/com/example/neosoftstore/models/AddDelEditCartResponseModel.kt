package com.example.neosoftstore.models

data class AddDelEditCartResponseModel(
    val data: Boolean,
    val message: String,
    val status: Int,
    val total_carts: Int,
    val user_msg: String
)