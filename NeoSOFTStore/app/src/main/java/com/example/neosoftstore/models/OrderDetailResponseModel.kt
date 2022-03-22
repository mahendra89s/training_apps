package com.example.neosoftstore.models

data class OrderDetailResponseModel(
    val `data`: OrderDetailData,
    val status: Int,
    val message : String,
    val user_msg : String,
)
data class OrderDetailData(
    val address: String,
    val cost: Int,
    val id: Int,
    val order_details: List<OrderDetail>
)
data class OrderDetail(
    val id: Int,
    val order_id: Int,
    val prod_cat_name: String,
    val prod_image: String,
    val prod_name: String,
    val product_id: Int,
    val quantity: Int,
    val total: Int
)