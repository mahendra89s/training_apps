package com.example.crypto.ui.coin_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crypto.remote.repository.CoinRepository

class CoinDetailViewModelFactory(
    val repository: CoinRepository,
    val id : String,
    val context: Context
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinDetailViewModel(repository,id,context) as T
    }
}