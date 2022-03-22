package com.example.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"GEOFENCE TRIGGERED....",Toast.LENGTH_LONG).show()
        val geofenceEvent = GeofencingEvent.fromIntent(intent!!)
        if(geofenceEvent.hasError()){
            Toast.makeText(context,"Error....",Toast.LENGTH_LONG).show()
            return
        }
        val geofenceList = geofenceEvent.triggeringGeofences
        when(geofenceEvent.geofenceTransition){
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(context,"GEOFENCE Entered....",Toast.LENGTH_LONG).show()
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context,"GEOFENCE Exit....",Toast.LENGTH_LONG).show()
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context,"GEOFENCE Dwelling....",Toast.LENGTH_LONG).show()
            }
        }
    }
}