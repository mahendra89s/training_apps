package com.example.cameraapp.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cameraapp.R
//import com.example.cameraapp.Model.Photo
import kotlinx.android.synthetic.main.photo_layout.view.*

class ViewPhotosAdapter(
    val images : MutableList<String>,
    val context: Context,
    val listener: ItemClickListener
) : RecyclerView.Adapter<ViewPhotosAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(images[position]).into(holder.itemView.imgPhoto)
        holder.itemView.imgPhoto.setOnLongClickListener {
            listener.onLongClick(position,it)
            true
        }
        holder.itemView.imgPhoto.setOnClickListener {
            listener.onClick(position,it)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}