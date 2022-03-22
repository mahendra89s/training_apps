package com.example.neosoftstore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neosoftstore.R
import com.example.neosoftstore.models.OrderDetail
import kotlinx.android.synthetic.main.item_order_detail.view.*

class OrderDetailAdapter(
    val orderItem : MutableList<OrderDetail>,
    val context: Context
) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail,parent,false))
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(orderItem[position].prod_image).into(holder.itemView.imgProductOrderDetail)
        holder.itemView.tvProductNameOrderDetail.text = orderItem[position].prod_name
        holder.itemView.tvCategoryNameOrderDetail.text = String.format(
            context.getString(R.string.categoryMyCart),
            orderItem[position].prod_cat_name
        )
        holder.itemView.tvQuantityOrderDetail.text = String.format(
            context.getString(R.string.quantity),
            orderItem[position].quantity
        )
        holder.itemView.tvPriceOrderDetail.text = String.format(
            context.getString(R.string.priceMyCart),
            orderItem[position].total
        )
    }

    override fun getItemCount(): Int {
        return orderItem.size
    }

}