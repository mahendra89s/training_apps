package com.example.harrypotter.ui.students

import com.example.harrypotter.data.model.Student

interface StudentListClickListener {
    fun onClick(student: Student)
}