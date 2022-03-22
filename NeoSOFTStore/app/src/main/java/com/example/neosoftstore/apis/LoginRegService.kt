package com.example.neosoftstore.apis

import com.example.neosoftstore.models.ForgetPasswordResponseModel
import com.example.neosoftstore.models.LoginResponse
import com.example.neosoftstore.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface LoginRegService {
    @FormUrlEncoded
    @POST("users/register")
    fun registerUser(
        @Field("first_name") first_name : String,
        @Field("last_name") last_name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("confirm_password") confirm_password : String,
        @Field("gender") gender : String,
        @Field("phone_no") phone_no : Long
    ) : Call<RegisterResponse>
    @FormUrlEncoded
    @POST("users/login")
    fun loginUser(
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("users/forgot")
    fun forgetPassword(
        @Field("email") email : String
    ) : Call<ForgetPasswordResponseModel>
}