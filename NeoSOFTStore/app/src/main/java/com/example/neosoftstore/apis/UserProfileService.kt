package com.example.neosoftstore.apis

import com.example.neosoftstore.models.EditProfileResponse
import com.example.neosoftstore.models.UserInfoModel
import retrofit2.Call
import retrofit2.http.*

interface UserProfileService {

    @GET("users/getUserData")
    fun getUserDetails(
        @Header("access_token") accessToken : String,
        @Query("access_token") access_token : String
    ) : Call<UserInfoModel>

    @FormUrlEncoded
    @POST("users/update")
    fun editUserDetails(
        @Header("access_token") accessToken : String,
        @Field("first_name") first_name : String,
        @Field("last_name") last_name : String,
        @Field("email") email : String,
        @Field("dob") dob : String,
        @Field("profile_pic") profile_pic : String,
        @Field("phone_no") phone_no : String,
    ) : Call<EditProfileResponse>

    @FormUrlEncoded
    @POST("users/change")
    fun resetPassword(
        @Header("access_token") accessToken : String,
        @Field("old_password") old_password : String,
        @Field("password") password : String,
        @Field("confirm_password") confirm_password : String,
    ) : Call<EditProfileResponse>
}