package com.example.crypto.ui.coin_details

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypto.remote.model.CoinDetail
import com.example.crypto.remote.repository.CoinRepository
import com.example.crypto.utils.Coroutines
import retrofit2.Response

class CoinDetailViewModel(
    private val repository: CoinRepository,
    private val coinId : String,
    context: Context
): ViewModel(){
    val coinDetail = MutableLiveData<CoinDetail>()
    lateinit var response: Response<CoinDetail>

    val _coinDetail : LiveData<CoinDetail>
        get() = coinDetail

    init {
        Coroutines.main {
            try {
                response = repository.getCoinDetail(coinId)
                coinDetail.value = response.body()
            }
            catch (e : Exception){
                Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }
    }

    fun getCoinDetail() : LiveData<CoinDetail> = _coinDetail
}