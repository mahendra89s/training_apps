package com.example.todoapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTask(taskEntry: TaskEntry) : Long

    @Query("SELECT * FROM tasks ORDER BY priority")
    fun getTasks() : LiveData<MutableList<TaskEntry>>

    @Delete
    fun deleteTask(taskEntry: TaskEntry)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskFromId(id : Int) : TaskEntry
}