package com.example.alarmapp

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var player: MediaPlayer
    lateinit var request : OneTimeWorkRequest
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player = MediaPlayer.create(applicationContext,R.raw.music)
        setbtn.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance()

            val nowMillis = calendar.timeInMillis
            val nowSecs = nowMillis / 1000

            val nowSecsRem = calendar.time.seconds
            Log.d("sec",nowSecs.toString())

            calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                timePicker.hour,
                timePicker.minute)
            val setMin = calendar.timeInMillis / 1000

            val difference =  setMin - nowSecs - nowSecsRem
            Log.d("nowMin",nowSecs.toString())
            Log.d("setMin",setMin.toString())
            Log.d("diff",difference.toString())


            request = OneTimeWorkRequestBuilder<AlarmWorker>()
                .setInitialDelay(difference,TimeUnit.SECONDS)
//                .setInputData(data)
                .build()

            WorkManager.getInstance(this).enqueue(request)
            Toast.makeText(this,"Alarm set for ${timePicker.hour}: ${timePicker.minute}",Toast.LENGTH_LONG).show()

        }

        btnStop.setOnClickListener {
            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                PeriodicWorkManager::class.java,
                16,
                TimeUnit.MINUTES
            ).build()
            WorkManager.getInstance(this).enqueue(periodicWorkRequest)
        }


    }
}