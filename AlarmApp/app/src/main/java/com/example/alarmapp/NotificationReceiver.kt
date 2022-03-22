package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.MediaStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationReceiver(
    val player: MediaPlayer
): BroadcastReceiver() {
    lateinit var gson: Gson
    override fun onReceive(context: Context?, intent: Intent?) {
        player.stop()
    }
}