package com.example.neosoftstore.models

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("data") val data: List<Data>,
    @SerializedName("status") val status: Int
)
data class Data(
    @SerializedName("id") val id : Int,
    @SerializedName("product_category_id") val productCategoryId : Int,
    @SerializedName("name") val name : String?,
    @SerializedName("producer") val producer : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("cost") val cost : Int,
    @SerializedName("rating") val rating : Int,
    @SerializedName("view_count") val viewCount : Int,
    @SerializedName("created") val created : String?,
    @SerializedName("modified") val modified : String?,
    @SerializedName("product_images") val productImages : String?
)