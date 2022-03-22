package com.example.crypto.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypto.remote.model.Coin
import com.example.crypto.remote.repository.CoinRepository
import com.example.crypto.utils.Coroutines
import retrofit2.Response

class MainViewModel(
    val repository : CoinRepository,
    context : Context
) : ViewModel() {
    var coinsLiveData = MutableLiveData<List<Coin>>()
    lateinit var response : Response<List<Coin>>
    val _coinsList :LiveData<List<Coin>>
        get() = coinsLiveData
    init {
        Coroutines.main {
            try{
                response = repository.getCoins()
                coinsLiveData.value = response.body()
            }
            catch(e : Exception){
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getCoins() : LiveData<List<Coin>> = _coinsList
}