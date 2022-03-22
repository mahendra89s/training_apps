package com.example.mvvmarchitecture.data.network.responses

import com.example.mvvmarchitecture.data.db.entities.User

data class AuthResponse(
    val data: User,
    val message: String,
    val status: Int,
    val user_msg: String
)