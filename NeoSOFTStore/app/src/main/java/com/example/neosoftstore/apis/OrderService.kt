package com.example.neosoftstore.apis

import com.example.neosoftstore.models.MyOrderResponseModel
import com.example.neosoftstore.models.OrderDetailResponseModel
import com.example.neosoftstore.models.PlaceOrderResponseModel
import retrofit2.Call
import retrofit2.http.*

interface OrderService {
    @FormUrlEncoded
    @POST("order")
    fun placeOrder(
        @Header("access_token") access_token : String,
        @Field("address") address : String,
    ) : Call<PlaceOrderResponseModel>

    @GET("orderList")
    fun getOrderList(
        @Header("access_token") access_token : String,
    ) : Call<MyOrderResponseModel>

    @GET("orderDetail")
    fun getOrderDetails(
        @Header("access_token") access_token : String,
        @Query("order_id") order_id : Int,
    ) : Call<OrderDetailResponseModel>
}