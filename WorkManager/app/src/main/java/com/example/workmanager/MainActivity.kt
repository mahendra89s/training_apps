package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val data = Data.Builder()
//            .putString("key_task","Hello Sending Data")
//            .build()
//
//        val constraints = Constraints.Builder()
//            .setRequiresCharging(true)
//            .build()
//
//        val request : OneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
//            .setInputData(data)
//            .setConstraints(constraints)
//            .build()

        val periodicRequest : PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            MyWorkerPeriodic::class.java,
               20,
            TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(this).enqueue(periodicRequest)

//        btn.setOnClickListener {
//            WorkManager.getInstance().enqueue(request)
//        }
//
//        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
//            .observe(this, {
//                if(it != null) {
//                    if(it.state.isFinished){
//                        val data = it.outputData
//                        val output = data.getString("key_output")
//                        txt.append(output)
//                    }
//                    val status: String = it.state.name
//                    txt.append("$status : \n")
//                }
//            })
    }
}