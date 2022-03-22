package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    @GET("data/2.5/onecall?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("exclude") exclude: String,
        @Query("appid") appid: String?,
        @Query("units") units : String
    ): Call<ForecastResponse>

    @GET("geo/1.0/direct?")
    fun getCityInfo(
        @Query("q") q : String,
        @Query("limit") limit:String,
        @Query("appid") appid: String
    ) : Call<List<CityInfoResponse>>
}