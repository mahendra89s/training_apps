package com.example.weatherapp

data class CityInfoResponse(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String
)