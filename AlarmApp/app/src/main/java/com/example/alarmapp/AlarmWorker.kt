package com.example.alarmapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class AlarmWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams){


    lateinit var notificationManager : NotificationManager
    lateinit var pendingIntent: PendingIntent
    override fun doWork(): Result {
        displayNotification("Alarm","Alarm Ringing!!!")
        val intent = Intent(applicationContext,MediaPlayerService::class.java)
        intent.action = "START"
        pendingIntent = PendingIntent.getService(applicationContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        pendingIntent.send()
        return Result.success()
    }

    private fun displayNotification(task : String, desc : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel"
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(applicationContext,MediaPlayerService::class.java)
        intent.action = "STOP"
        pendingIntent = PendingIntent.getService(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)


        val builder : Notification = NotificationCompat.Builder(applicationContext,"Notification")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_alarm)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .addAction(R.drawable.ic_alarm,"Stop",pendingIntent)
            .setTicker("Notifications")
            .build()
        notificationManager.notify(0,builder)
    }
}