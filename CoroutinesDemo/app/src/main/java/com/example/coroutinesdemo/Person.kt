package com.example.coroutinesdemo

import java.io.Serializable

data class Person (
        val firstName : String,
        val lastName : String,
        val dob : String,
        val country : String
    ):Serializable