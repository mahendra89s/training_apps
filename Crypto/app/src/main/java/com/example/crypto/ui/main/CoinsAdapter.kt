package com.example.crypto.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.R
import com.example.crypto.databinding.CoinsLayoutBinding
import com.example.crypto.remote.model.Coin

class CoinsAdapter(
    val coins : List<Coin>,
    val listener: itemClickListener
) : RecyclerView.Adapter<CoinsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : CoinsLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.coins_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: CoinsAdapter.ViewHolder, position: Int) {
        holder.binding.coin = coins[position]
        listener.onClick(coins[position].id)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

}