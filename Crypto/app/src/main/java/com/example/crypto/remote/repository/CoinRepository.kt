package com.example.crypto.remote.repository

import com.example.crypto.remote.model.Coin
import com.example.crypto.remote.model.CoinDetail
import com.example.crypto.remote.network.CoinService
import retrofit2.Response

class CoinRepository {
    suspend fun getCoins() : Response<List<Coin>>{
        return CoinService().getCoins()
    }
    suspend fun getCoinDetail(
        coinId : String
    ) : Response<CoinDetail>{
        return CoinService().getCoinDetail(coinId)
    }
}