package com.example.geofence
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.geofence.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var googleApiClient : GoogleApiClient
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var binding: ActivityMapsBinding
    private val FINE_LOCATION_ACCESS_REQUEST_CODE = 100
    private lateinit var locationManager : LocationManager
    private lateinit var geofenceHelper: GeofenceHelper
    private var GEOFENCE_ID = "SOME_GEOFENCE_ID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        geofencingClient = LocationServices.getGeofencingClient(this)
        geofenceHelper = GeofenceHelper(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        enableUserLocation()


        mMap.setOnMapLongClickListener{
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it).title("Marked"))
            mMap.addCircle(
                CircleOptions().
                center(it).
                radius(500.0).
                strokeColor(Color.argb(255,255,0,0)).
                fillColor(Color.argb(64,255,0,0)).
                strokeWidth(4F)
            )
            addGeofence(it,500F)
        }
    }
    private fun addGeofence(latLng: LatLng,radius : Float){
        val geofence = geofenceHelper.getGeofence(
            GEOFENCE_ID,
            latLng,
            radius,
            Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
        )
        val geofenceRequest = geofenceHelper.geofencingRequest(geofence)
        val pendingIntent = geofenceHelper.getPendingIntent()


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        geofencingClient.addGeofences(geofenceRequest,pendingIntent!!)
//            .addOnSuccessListener {
//                Log.d("ON SUCCESS","GEOFENCE ADDED....")
//            }
//            .addOnFailureListener {
//                val errorString = getErrorString(it)
//                Log.d("ON FAILURE",errorString)
//            }
    }

    private fun enableUserLocation(){
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        mMap.isMyLocationEnabled = true

        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_ACCESS_REQUEST_CODE)
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_ACCESS_REQUEST_CODE)
            }
        }
    }
//    private fun turnGPSOn(context: Context) : Boolean{
//        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//            if(!gpsStatus){
//
//                return true
//            }
//            return false
//        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mMap.isMyLocationEnabled = true
            }
        }
    }
    fun getErrorString(e : Exception) : String{
        if(e is ApiException){
            val apiException = e as ApiException
            when(apiException.statusCode){
                GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> {
                    return "GEOFENCE NOT AVAILABLE"
                }
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES ->{
                    return "GEOFENCE TOO MANY GEOFENCES"
                }
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> {
                    return "GEOFENCE TOO MANY PENDING INTENTS"
                }
            }
        }
        return e.localizedMessage
    }
}