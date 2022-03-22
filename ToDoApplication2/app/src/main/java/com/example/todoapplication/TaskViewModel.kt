package com.example.todoapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.TaskEntry

class TaskViewModel(
    var appDatabase: AppDatabase,
    val mTaskId : Int,
    val context: Context
) : ViewModel() {
    var tasks : LiveData<MutableList<TaskEntry>> = appDatabase.getTaskDao().getTasks()

    @JvmName("getTasks1")
    fun getTasks() : LiveData<MutableList<TaskEntry>>{
        return tasks
    }
}