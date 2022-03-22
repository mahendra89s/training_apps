package com.example.neosoftstore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftstore.R
import com.example.neosoftstore.models.MyOrderData
import kotlinx.android.synthetic.main.item_my_order.view.*

class MyOrderAdapter(
    val context: Context,
    val order : MutableList<MyOrderData>,
    val listener : MyOrderListener
) : RecyclerView.Adapter<MyOrderAdapter.ViewHolder>(){
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_order,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvOrderIDMyOrder.text = String.format(
            context.getString(R.string.orderId),
            order[position].id)
        holder.itemView.tvOrderDateMyOrder.text = String.format(
            context.getString(R.string.orderDate),
            order[position].created
        )
        holder.itemView.tvOrderPriceMyOrder.text = String.format(
            context.getString(R.string.priceMyCart),
            order[position].cost
        )
        holder.itemView.myOrderContainer.setOnClickListener {
            listener.orderItemClicked(order[position].id)
        }
    }

    override fun getItemCount(): Int {
        return order.size
    }

}