package com.example.todoapplication

import android.R.attr
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.text.SimpleDateFormat
import java.util.*


import androidx.core.content.ContextCompat
import com.example.todoapplication.database.TaskEntry
import kotlinx.android.synthetic.main.task_layout.view.*





class TaskAdapter(
    val context: Context,
    val listener: ItemClickListener,

): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    lateinit var taskEntry : TaskEntry
    private var taskEntries: MutableList<TaskEntry> = mutableListOf()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskEntry = taskEntries[position]
        holder.itemView.tvDesc.text = taskEntry.description
        holder.itemView.tvDate.text = dateFormat.format(taskEntry.updatedAt)
        holder.itemView.tvPriority.text = taskEntry.priority.toString()

        val priorityCircle : GradientDrawable = holder.itemView.tvPriority.background as GradientDrawable

        val priorityColor = getPriorityColor(taskEntry.priority)
        priorityCircle.setColor(priorityColor)

        holder.itemView.setOnClickListener {
            val elementId = taskEntries[position].id
            listener.onItemClicked(elementId)
        }
    }
    private fun getPriorityColor(priority: Int): Int {
        var priorityColor = 0
        when (priority) {
            1 -> priorityColor = ContextCompat.getColor(context, R.color.materialRed)
            2 -> priorityColor = ContextCompat.getColor(context, R.color.materialOrange)
            3 -> priorityColor = ContextCompat.getColor(context, R.color.materialYellow)
            else -> {
            }
        }
        return priorityColor
    }
    fun setTasks(taskEntries: MutableList<TaskEntry>) {
        this.taskEntries = taskEntries
        notifyDataSetChanged()
    }
    fun getTasks() : MutableList<TaskEntry>{
        return taskEntries
    }
    override fun getItemCount(): Int {
        if(taskEntries == null){
            return 0
        }
        return taskEntries.size
    }

}