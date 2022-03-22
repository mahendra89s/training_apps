package com.example.neosoftstore.models

import com.google.gson.annotations.SerializedName

data class GetProductInfoModel(
    @SerializedName("data") val data: GetProductInfoModelData,
    @SerializedName("status") val status: Int
)
data class GetProductInfoModelData(
    val cost: Int,
    val created: String,
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val producer: String,
    val product_category_id: Int,
    val product_images: List<ProductImage>,
    val rating: Int,
    val view_count: Int
)
data class ProductImage(
    val created: String,
    val id: Int,
    val image: String,
    val modified: String,
    val product_id: Int
)