package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = inputData
        val desc = data.getString("key_task")
        displayNotification("Hey I am Work",desc!!)

        val data1 = Data.Builder()
            .putString("key_output","Task Completed Successfully!!")
            .build()


        return Result.success(data1)
    }
    private fun displayNotification(task : String, desc : String){
        val manager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel : NotificationChannel = NotificationChannel("Notification","Notification",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,"Notification")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.mipmap.ic_launcher)
        manager.notify(1,builder.build())
    }
}