package com.example.harrypotter.ui.students

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harrypotter.data.repository.StudentRepository
import com.example.harrypotter.ui.LoadingDialog

class StudentViewModelFactory(
    val context: Context,
    val house : String,
    val repository: StudentRepository,
    val dialog: LoadingDialog
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentViewModel(context,house,repository,dialog) as T
    }
}