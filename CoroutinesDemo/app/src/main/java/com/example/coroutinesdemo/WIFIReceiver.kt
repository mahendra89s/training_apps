package com.example.coroutinesdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Switch
import android.widget.Toast

class WIFIReceiver (var wifiSwitch: Switch): BroadcastReceiver() {

    override fun onReceive(context: Context?,intent: Intent?) {
        when(intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN)){
            WifiManager.WIFI_STATE_ENABLED -> {
                wifiSwitch.isChecked = true
                wifiSwitch.text = "WIFI ON"
                Toast.makeText(context,"WIFI ON",Toast.LENGTH_LONG).show()

            }
            WifiManager.WIFI_STATE_DISABLED -> {
                wifiSwitch.isChecked = false
                wifiSwitch.text = "WIFI OFF"
                Toast.makeText(context,"WIFI OFF",Toast.LENGTH_LONG).show()
            }
        }
    }

}