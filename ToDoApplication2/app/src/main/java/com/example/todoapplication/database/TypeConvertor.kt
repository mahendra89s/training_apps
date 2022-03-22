package com.example.todoapplication.database

import androidx.room.TypeConverter
import java.util.*

class TypeConvertor {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}