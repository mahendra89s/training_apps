package com.example.coroutinesdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_comments.view.*

class CommentAdapter(
    val comments : List<Comments>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_comments,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtName.text = comments[position].name.toString()
        holder.itemView.txtComments.text = comments[position].body.toString()
    }

    override fun getItemCount(): Int {
        return comments.size
    }

}