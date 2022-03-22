package com.example.coroutinesdemo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_todo.view.*

class TodoAdapter (
        var todos : MutableList<Todo>
    ):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_todo,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            tvTitle.text = todos[position].title
            checkDone.isChecked = todos[position].ischecked
        }

        holder.itemView.tvDelete.setOnClickListener {
            todos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,todos.size)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}