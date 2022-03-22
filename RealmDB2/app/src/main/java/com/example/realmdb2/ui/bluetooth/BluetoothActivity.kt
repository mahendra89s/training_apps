package com.example.realmdb2.ui.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.realmdb2.R
import com.example.realmdb2.utils.showToast

const val REQUEST_ENABLE_BT = 101
class BluetoothActivity : AppCompatActivity() {
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var pairedDevices : Set<BluetoothDevice>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

    }

    override fun onStart() {
        super.onStart()
        val bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        if(bluetoothManager.adapter != null){
            bluetoothAdapter = bluetoothManager.adapter

            if(!bluetoothAdapter.isEnabled){
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }

        }

    }

    private fun getPairedDevices(){
        pairedDevices = bluetoothAdapter.bondedDevices
        Log.d("paired_devices", pairedDevices.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK){
            this.showToast("Bluetooth Enabled!!")
        }
    }
}