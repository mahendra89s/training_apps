package com.example.alarmapp

import android.app.Notification
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class PeriodicWorkManager(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    lateinit var player: MediaPlayer

    override fun doWork(): Result {
        displayNotification("Alarm","Alarm Ringing!!!")
        return Result.success()
    }
    private fun displayNotification(task : String, desc : String){
//        val manager : NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val channel : NotificationChannel = NotificationChannel("Notification","Notification",
//                NotificationManager.IMPORTANCE_HIGH)
//            manager.createNotificationChannel(channel)
//        }

//        val actionIntent : Intent = Intent(applicationContext,NotificationReceiver::class.java)
//        val broadcast = PendingIntent.getBroadcast(applicationContext,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT)

//        val intent = Intent(applicationContext,MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,0)
        val builder : Notification = NotificationCompat.Builder(applicationContext,"Notification")
            .setContentTitle(task)
            .setContentText(desc)
            .setSmallIcon(R.drawable.ic_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)
            .setTicker("Notifications")
            .build()

        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(0,builder)
    }

}