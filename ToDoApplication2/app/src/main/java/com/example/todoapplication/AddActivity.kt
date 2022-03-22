package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent
import kotlinx.android.synthetic.main.activity_add.*

import android.view.View

import android.widget.RadioGroup
import com.example.todoapplication.database.AppDatabase
import com.example.todoapplication.database.TaskEntry
import java.util.*

class AddActivity : AppCompatActivity() {
    // Extra for the task ID to be received in the intent
    val extraTaskId = "extraTaskId"

    // Extra for the task ID to be received after rotation
    val instanceTaskId = "instanceTaskId"

    // Constants for priority
    val priorityHigh = 1
    val priorityMedium = 2
    val priorityLow = 3
    private val defaultTaskId = -1
    private var mTaskId = defaultTaskId
    // Constant for default task id to be used when not in update mode

    lateinit var appDatabase: AppDatabase
    lateinit var taskEntry: TaskEntry
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        appDatabase = AppDatabase.invoke(applicationContext)
        if (savedInstanceState != null && savedInstanceState.containsKey(instanceTaskId)) {
            mTaskId = savedInstanceState.getInt(instanceTaskId, defaultTaskId)
        }

        val intent = intent
        if (intent != null && intent.hasExtra(extraTaskId)) {
            btnSave.text = "Update"
            if(mTaskId == defaultTaskId) {
                mTaskId = intent.getIntExtra(extraTaskId,defaultTaskId)
                taskEntry = appDatabase.getTaskDao().getTaskFromId(mTaskId)
                populateUI(taskEntry)
            }
        }
        btnSave.setOnClickListener {
            onSaveButtonClicked()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(instanceTaskId, mTaskId)
        super.onSaveInstanceState(outState)
    }
    fun onSaveButtonClicked() {
        val desc = edtTaskDesc.text.toString()
        val priority = getPriorityFromViews()
        val date = Date()
        val taskEntry = TaskEntry(desc,priority,date)
        if(mTaskId == defaultTaskId){
            appDatabase.getTaskDao().upsertTask(taskEntry)
        }
        else{
            taskEntry.id = mTaskId
            appDatabase.getTaskDao().upsertTask(taskEntry)
        }
        finish()
    }
    private fun populateUI(task: TaskEntry) {
        if(task == null){
            return
        }
        edtTaskDesc.setText(task.description)
        setPriorityInViews(task.priority)
    }
    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    fun getPriorityFromViews(): Int {
        var priority = 1
        val checkedId = grpPriority.checkedRadioButtonId
        when (checkedId) {
            btnHigh.id -> priority = priorityHigh
            btnMedium.id -> priority = priorityMedium
            btnLow.id -> priority = priorityLow
        }
        return priority
    }
    fun setPriorityInViews(priority: Int) {
        when (priority) {
            priorityHigh -> grpPriority.check(btnHigh.id)
            priorityMedium -> grpPriority.check(btnMedium.id)
            priorityLow -> grpPriority.check(btnLow.id)
        }
    }
}