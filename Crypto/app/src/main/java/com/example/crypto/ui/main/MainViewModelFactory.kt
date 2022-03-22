package com.example.crypto.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crypto.remote.repository.CoinRepository

class MainViewModelFactory(
    val repository: CoinRepository,
    val context: Context
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository,context) as T
    }
}