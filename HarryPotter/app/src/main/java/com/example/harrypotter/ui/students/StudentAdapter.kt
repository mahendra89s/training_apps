package com.example.harrypotter.ui.students

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotter.R
import com.example.harrypotter.data.model.Student
import com.example.harrypotter.databinding.StudentListLayoutBinding

class StudentAdapter(
    private val studentList : List<Student>,
    val listener: StudentListClickListener
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {


    inner class ViewHolder(val binding : StudentListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.student_list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.student = studentList[position]
        holder.binding.cardView.setOnClickListener {
            listener.onClick(studentList[position])
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }


}