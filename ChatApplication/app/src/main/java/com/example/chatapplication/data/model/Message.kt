package com.example.chatapplication.data.model

data class Message(
    val sender : String,
    val receiver : String,
    val TimeStamp : Long,
    val message : String,
)
