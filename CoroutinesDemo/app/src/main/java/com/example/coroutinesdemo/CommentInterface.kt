package com.example.coroutinesdemo

import retrofit2.Call
import retrofit2.http.GET

interface CommentInterface {

    @GET("comments")
    fun getComments() : Call<List<Comments>>
}