package com.example.neosoftstore.models

data class RatingResponseModel(
    val data: RatingResponseData,
    val message: String,
    val status: Int,
    val user_msg: String
)
data class RatingResponseData(
    val cost: Int,
    val created: String,
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val producer: String,
    val product_category_id: Int,
    val product_images: String,
    val rating: Float,
    val view_count: Int
)