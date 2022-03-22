package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerPeriodic(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
//        displayNotification("Hey I am Work","Periodic Task!!!")
        Log.d("Hello","Periodic!!!")
        return Result.success()
    }
//    private fun displayNotification(task : String, desc : String){
//        val manager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val channel : NotificationChannel = NotificationChannel("Notification","Notification",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            manager.createNotificationChannel(channel)
//        }
//        val builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,"Notification")
//            .setContentTitle(task)
//            .setContentText(desc)
//            .setSmallIcon(R.mipmap.ic_launcher)
//        manager.notify(1,builder.build())
//    }
}