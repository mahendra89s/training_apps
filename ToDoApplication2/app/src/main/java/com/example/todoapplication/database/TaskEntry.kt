package com.example.todoapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val CURRENT_USER_ID = 0
@Entity(tableName = "tasks")
data class TaskEntry(
    val description : String,
    val priority : Int,
    @ColumnInfo(name = "updated_at") val updatedAt : Date
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = CURRENT_USER_ID
}
