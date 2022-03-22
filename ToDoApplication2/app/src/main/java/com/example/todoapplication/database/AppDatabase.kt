package com.example.todoapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TaskEntry::class],
    version = 1
)
@TypeConverters(TypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao

    companion object{

        private var instance : AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ToDoApplication.db"
                )
                .allowMainThreadQueries()
                .build()
    }
}