package com.example.neosoftstore.apis

import com.example.neosoftstore.models.GetProductInfoModel
import com.example.neosoftstore.models.ProductModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products/getList")
    fun getProductList(
        @Query("product_category_id") product_category_id : Int
    ) : Call<ProductModel>

    @GET("products/getDetail")
    fun getProductInfo(
        @Query("product_id") product_id : Int
    ) : Call<GetProductInfoModel>
}