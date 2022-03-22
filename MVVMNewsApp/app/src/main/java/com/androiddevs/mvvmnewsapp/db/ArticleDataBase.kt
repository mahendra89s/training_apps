package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.androiddevs.mvvmnewsapp.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class ArticleDataBase {

    abstract fun getAllArticles():ArticleDao

    companion object{
        @Volatile
        private var instance: ArticleDataBase? = null
        private val  LOCK = Any()

//        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
//            instance ?: createDatabase(context).also{ instance = it }
//        }
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                ArticleDataBase::class.java,
//                "article_db.db"
//            ).build()
    }
}