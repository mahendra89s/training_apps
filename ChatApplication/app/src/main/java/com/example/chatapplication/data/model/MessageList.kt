package com.example.chatapplication.data.model

data class MessageList(
    val name : String,
    val number : String,
    val lastMessage : String,
    val unseenMessages : Int,
    val status : String
)
