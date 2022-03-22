package com.example.neosoftstore.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neosoftstore.R
import com.example.neosoftstore.models.ListCartData
import kotlinx.android.synthetic.main.item_mycart.view.*
import java.util.*

class CartListAdapter(
    val data : MutableList<ListCartData>,
    val context : Context,
    val listener: CartListener
): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mycart,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Glide.with(context).load(data[position].product.product_images).into(holder.itemView.imgProductMyCart)
        holder.itemView.tvProductNameMyCart.text = data[position].product.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.itemView.tvCategoryNameMyCart.text = String.format(context.getString(R.string.categoryMyCart),data[position].product.product_category)
        holder.itemView.tvPriceMyCart.text =  String.format(context.getString(R.string.priceMyCart),data[position].product.cost)
//        holder.itemView.tvQuantityMyCart.text = data[position].quantity.toString()
        val quantityList = listOf(1,2,3,4,5,6,7,8)
        val adapterQuantity = ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,quantityList)
        holder.itemView.spinnerMyCart.adapter = adapterQuantity
        holder.itemView.spinnerMyCart.setSelection(data[position].quantity-1)
        holder.itemView.spinnerMyCart.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(data[position].quantity != quantityList[p2]){
//                    Toast.makeText(context, quantityList[p2].toString(),Toast.LENGTH_LONG).show()
//                    holder.itemView.tvQuantityMyCart.text = quantityList[p2].toString()
                    listener.editItem(data[position].product_id,quantityList[p2])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    fun deleteItem(index : Int){
        listener.deleteItem(data[index].product_id)
        data.removeAt(index)

        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return data.size
    }

}