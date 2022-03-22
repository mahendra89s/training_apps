package com.example.neosoftstore.apis

import com.example.neosoftstore.models.RatingResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface RatingService {
    @FormUrlEncoded
    @POST("products/setRating")
    fun setRating(
        @Field("product_id") product_id : Int,
        @Query("rating") rating : Int?
    ) : Call<RatingResponseModel>
}