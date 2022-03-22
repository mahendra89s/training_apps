package com.example.neosoftstore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftstore.R
import com.example.neosoftstore.models.StoreLocatorModel
import kotlinx.android.synthetic.main.item_store_locator.view.*

class StoreLocatorAdapter(
    val address : MutableList<StoreLocatorModel>,
    val context: Context,
    val listener: StoreLocatorListener
) : RecyclerView.Adapter<StoreLocatorAdapter.ViewHolder>() {
    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreLocatorAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_store_locator,parent,false))
    }

    override fun onBindViewHolder(holder: StoreLocatorAdapter.ViewHolder, position: Int) {
        holder.itemView.storeName.text = address[position].name
        holder.itemView.storeAddress.text = address[position].address
        holder.itemView.storeLocatorContainer.setOnClickListener {
            listener.onStoreListClick(address[position].lat,address[position].long,position)
        }
    }

    override fun getItemCount(): Int {
        return address.size
    }

}