package com.example.mvvmarchitecture.data.network

import com.example.mvvmarchitecture.data.network.responses.AuthResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyAPI {
    @FormUrlEncoded
    @POST("users/login")
    suspend fun userLogin(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("users/register")
    suspend fun registerUser(
        @Field("first_name") first_name : String,
        @Field("last_name") last_name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("confirm_password") confirm_password : String,
        @Field("gender") gender : String,
        @Field("phone_no") phone_no : String,
    ) : Response<AuthResponse>

    companion object{
        operator fun invoke(
            networkInterceptor: NetworkInterceptor
        ):MyAPI{
            val client = OkHttpClient.Builder()
                .addInterceptor(networkInterceptor)
                .build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl("http://staging.php-dev.in:8844/trainingapp/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyAPI::class.java)
        }
    }
}