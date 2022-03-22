package com.example.harrypotter.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotter.data.model.Student

class DetailFactory(
    val student: Student
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(student) as T
    }
}