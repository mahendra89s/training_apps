package com.example.realmdb2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.realmdb2.R
import com.example.realmdb2.databinding.ActivityMainBinding
import com.example.realmdb2.ui.bluetooth.BluetoothActivity
//import com.example.realmdb2.utils.Utils
import com.example.realmdb2.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.mainViewModel = viewModel

        viewModel._message.observe(this,{
            this.showToast(it)
        })

        viewModel.workManager.getWorkInfoByIdLiveData(viewModel.oneTimePeriodicWorkRequest.id)
            .observe(this,{ workInfo ->
                when(workInfo?.outputData?.getString("status")){
                    "succes" -> {
                        this.showToast("Data Uploaded to Firebase!!!")
//                        Utils.showToast(this,"Data Uploaded to Firebase!!!")
                    }
                    "fail" -> {
                        this.showToast("Data Failed to upload!!!")
//                        Utils.showToast(this,"Data Failed to upload!!!")
                    }
                }

        })

//        val names = flowOf("hello","world","coding","programming")
//        runBlocking {
//            names
//                .map { name -> name.length }
//                .filter { length -> length > 5 }
//                .collect { println(it) }
//        }
        btnBluetooth.setOnClickListener {
            Intent(this,BluetoothActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}