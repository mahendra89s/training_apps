package com.example.demo_recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_layout.view.*

class ImageAdapter(
    val image : MutableList<ImageModel>,
    val context: Context,
    val imageListener : ItemClickListener

) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var lastTickPosition : Int = 0
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_layout,parent,false))
    }


    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(image[position].image).into(holder.itemView.images)
        if(position == lastTickPosition){
            holder.itemView.tick_image.visibility = View.VISIBLE
        }
        else{
            holder.itemView.tick_image.visibility = View.GONE
        }




        holder.itemView.setOnClickListener {
            if(position != lastTickPosition){
                imageListener.onclick(position,lastTickPosition)
                lastTickPosition = position
                notifyItemChanged(lastTickPosition)
                notifyItemChanged(position)
            }
        }
    }


    override fun getItemCount(): Int {
        return image.size
    }

}