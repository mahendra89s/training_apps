package com.example.coroutinesdemo

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_wifiactivity.*

class WIFIActivity : AppCompatActivity() {
    lateinit var receiver : WIFIReceiver
    lateinit var wifiManager : WifiManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifiactivity)
        receiver = WIFIReceiver(wifiSwitch)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION).also{
            registerReceiver(receiver,it)
        }
        wifiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                wifiManager.isWifiEnabled = true
                wifiSwitch.text = "WiFi is ON"
            } else {

                wifiManager.isWifiEnabled = false
                wifiSwitch.text = "WiFi is OFF"
            }
        }
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}