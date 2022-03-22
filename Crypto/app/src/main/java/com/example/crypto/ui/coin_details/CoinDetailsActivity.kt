package com.example.crypto.ui.coin_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.crypto.R
import com.example.crypto.databinding.ActivityCoinDetailsBinding
import com.example.crypto.remote.repository.CoinRepository

class CoinDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityCoinDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_coin_details)
        val repository = CoinRepository()
        val id = intent.getStringExtra("id")
        val viewmodel = ViewModelProvider(this,CoinDetailViewModelFactory(
            repository,id!!,this
        ))[CoinDetailViewModel::class.java]
        viewmodel._coinDetail.observe(this,{ coin ->
            binding.coin = coin
        })

    }
}