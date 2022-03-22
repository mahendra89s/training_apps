package com.example.crypto.remote.network

import com.example.crypto.remote.model.Coin
import com.example.crypto.remote.model.CoinDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {

    @GET("coins")
    suspend fun getCoins() : Response<List<Coin>>

    @GET("coins/{coinId}")
    suspend fun getCoinDetail(
        @Path("coinId") coinId : String
    ) : Response<CoinDetail>

    companion object{
        operator fun invoke() : CoinService{
            return Retrofit.Builder()
                .baseUrl("https://api.coinpaprika.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinService::class.java)
        }
    }
}