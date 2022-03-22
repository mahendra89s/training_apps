package com.example.neosoftstore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neosoftstore.ProductClickListener
import com.example.neosoftstore.R
import com.example.neosoftstore.models.Data
import kotlinx.android.synthetic.main.item_product_list.view.*
import java.util.*

class ProductListingAdapter(
    val products : MutableList<Data>,
    val context : Context,
    val listener: ProductClickListener
): RecyclerView.Adapter<ProductListingAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.productName.text = products[position].name!!.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.itemView.productDesc.text = products[position].producer!!.replaceFirstChar {
            if(it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        holder.itemView.productPrice.text = String.format(context.getString(R.string.price),products[position].cost)
        Glide.with(context).load(products[position].productImages).into(holder.itemView.productImage)
        val rating = products[position].rating
        val starArray  = listOf(holder.itemView.productStar1,
            holder.itemView.productStar2,
            holder.itemView.productStar3,
            holder.itemView.productStar4,
            holder.itemView.productStar5,
        )
        for(i in 0 until rating){
            Glide.with(context).load(R.drawable.star_check).into(starArray[i])
        }
        holder.itemView.productListContainer.setOnClickListener {

            listener.onProductClick(products[position].id)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}