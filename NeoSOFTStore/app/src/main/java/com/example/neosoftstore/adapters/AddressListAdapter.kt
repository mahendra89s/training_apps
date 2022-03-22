package com.example.neosoftstore.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neosoftstore.R
import com.example.neosoftstore.models.AddressModel
import kotlinx.android.synthetic.main.item_address_list.view.*

class AddressListAdapter(
    val address : MutableList<AddressModel>,
    val userName : String,
    val context: Context,
    val listener: AddressListener
): RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {
    private var checkedPos = -1
    private var addressFormat : String? = null
    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_address_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.itemView.tvNameAddress.text = userName

        holder.itemView.tvAddressListAddress.text = String.format(
            context.getString(R.string.address),
            address[position].address,
            address[position].landmark,
            address[position].city,
            address[position].zipcode,
            address[position].state,
            address[position].country
        )
        holder.itemView.deleteAddress.setOnClickListener {
            address.removeAt(position)
            listener.deleteAddress(position)
            notifyDataSetChanged()
        }
        if(checkedPos == position){
            Glide.with(context).load(R.drawable.ic_radio_checked).into(holder.itemView.radioAddress)
        }
        else{
            Glide.with(context).load(R.drawable.ic_radio_unchecked).into(holder.itemView.radioAddress)

        }
        holder.itemView.addressContainer.setOnClickListener {
            checkedPos = position
            listener.getAddress(String.format(
                context.getString(R.string.address),
                address[position].address,
                address[position].landmark,
                address[position].city,
                address[position].zipcode,
                address[position].state,
                address[position].country
            ))
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return address.size
    }
}