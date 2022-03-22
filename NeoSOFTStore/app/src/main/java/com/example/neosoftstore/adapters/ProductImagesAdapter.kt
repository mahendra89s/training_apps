package com.example.neosoftstore.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neosoftstore.ImageClickListener
import com.example.neosoftstore.R
import kotlinx.android.synthetic.main.item_images_details_screen.view.*

class ProductImagesAdapter(
    val images : MutableList<String>,
    val context : Context,
    val listener: ImageClickListener
) : RecyclerView.Adapter<ProductImagesAdapter.ViewHolder>() {
    private var selectedPosition : Int = 0
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_images_details_screen,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        Glide.with(context).load(images[position]).into(holder.itemView.imageDetailsScreen)
        holder.itemView.imageDetailsScreen.background = ContextCompat.getDrawable(context,
            R.drawable.grey_border_image
        )
        if (selectedPosition == position) {
            holder.itemView.imageDetailsScreen.background = ContextCompat.getDrawable(context,
                R.drawable.red_border_image
            )
        }
        holder.itemView.imageContainerDetailScreen.setOnClickListener {

            listener.imageClick(images[position])
            val previousItem: Int = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousItem)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}