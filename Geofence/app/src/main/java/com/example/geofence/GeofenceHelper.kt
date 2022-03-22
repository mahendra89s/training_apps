package com.example.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng

class GeofenceHelper(base: Context?) : ContextWrapper(base) {

    private var pendingIntent : PendingIntent? = null
    fun geofencingRequest(geofence : Geofence) : GeofencingRequest{
        return GeofencingRequest.Builder().
                addGeofence(geofence).
                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).
                build()
    }
    fun getGeofence(id : String,latLng: LatLng,radius : Float,transitionTypes: Int) : Geofence{
        return Geofence.
                Builder().
                setCircularRegion(latLng.latitude,latLng.longitude,radius).
                setRequestId(id).
                setTransitionTypes(transitionTypes).
                setLoiteringDelay(5000).
                setExpirationDuration(Geofence.NEVER_EXPIRE).
                build()
    }
    fun getPendingIntent() : PendingIntent? {
        if(pendingIntent != null){
            return pendingIntent
        }
        val intent = Intent(this,GeofenceBroadCastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,2607,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

}