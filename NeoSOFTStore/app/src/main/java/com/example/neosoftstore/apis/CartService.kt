package com.example.neosoftstore.apis

import com.example.neosoftstore.models.AddDelEditCartResponseModel
import com.example.neosoftstore.models.ListCartResponseModel
import retrofit2.Call
import retrofit2.http.*

interface CartService {
    @FormUrlEncoded
    @POST("addToCart")
    fun addCart(
        @Header("access_token") access_token : String,
        @Field("product_id") product_id : Int,
        @Field("quantity") quantity : Int
    ) : Call<AddDelEditCartResponseModel>

    @GET("cart")
    fun getCartList(
        @Header("access_token") access_token : String,
    ) : Call<ListCartResponseModel>

    @FormUrlEncoded
    @POST("deleteCart")
    fun deleteCart(
        @Header("access_token") access_token : String,
        @Field("product_id") product_id : Int,
    ) : Call<AddDelEditCartResponseModel>

    @FormUrlEncoded
    @POST("editCart")
    fun editCart(
        @Header("access_token") access_token : String,
        @Field("product_id") product_id : Int,
        @Field("quantity") quantity: Int,
    ) : Call<AddDelEditCartResponseModel>
}