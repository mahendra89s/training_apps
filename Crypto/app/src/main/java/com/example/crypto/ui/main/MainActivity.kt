package com.example.crypto.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crypto.R
import com.example.crypto.databinding.ActivityMainBinding
import com.example.crypto.remote.repository.CoinRepository
import com.example.crypto.ui.coin_details.CoinDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), itemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = CoinRepository()
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val viewModel = ViewModelProvider(this,MainViewModelFactory(repository,applicationContext))[MainViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.getCoins().observe(this,{ coins->
            coins_list_rv.also {
                val linearLayoutManager = LinearLayoutManager(this)
                it.layoutManager = linearLayoutManager
                it.adapter = CoinsAdapter(coins,this)
            }
        })
    }

    override fun onClick(id: String) {
        Intent(this,CoinDetailsActivity::class.java).also {
            it.putExtra("id",id)
            startActivity(it)
        }
    }
}