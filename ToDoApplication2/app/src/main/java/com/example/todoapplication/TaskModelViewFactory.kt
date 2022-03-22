package com.example.todoapplication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplication.database.AppDatabase

class TaskModelViewFactory(
    val appDatabase: AppDatabase,
    val mTaskId : Int,
    val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(appDatabase,mTaskId,context) as T
    }
}