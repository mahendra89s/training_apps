package com.example.weatherapp


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


const val API = "b72f936c90bf50d0c3cb543ce68ccb8b"
const val REQUEST_CODE_ADD_CITY = 1
const val LOCATION_PERMISSION_REQUEST_CODE = 1
class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var arrayList : MutableList<CityInfoResponse>
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLocationRequest : LocationRequest
    lateinit var mLocationCallback : LocationCallback
    lateinit var geocoder: Geocoder
    lateinit var progressDialog : ProgressDialog
    lateinit var locationManager : LocationManager
    var lat : Double? = null
    var lon : Double? = null
    var city : String? = null
    var country : String? = null
    var gpsStatus : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Custom Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder= Geocoder(this, Locale.getDefault())


        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        lat = location.latitude
                        lon = location.longitude
                        val address : MutableList<Address> = geocoder.getFromLocation(lat!!,lon!!,1)
                        city = address[0].locality
                        country = address[0].countryCode
                        Log.d("lat1",lat.toString())
                        Log.d("lon1",lon.toString())
                        Log.d("city1",city.toString())
                        Log.d("country1",country.toString())
                        sharedPrefCall()
                        if(fusedLocationProviderClient != null){
                            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                        }
                    }
                }
            }
        }

    }
    private fun sharedPrefCall(){
        progressDialog.dismiss()
        Log.d("dsds","sdsds")
        sharedPreferences = getSharedPreferences("CityList3", AppCompatActivity.MODE_PRIVATE)!!

//        sharedPreferences.edit().clear().apply()
        val prefData: String? = sharedPreferences.getString("CityList3","")
        val gson = Gson()
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        val type = object : TypeToken<List<CityInfoResponse?>?>() {}.type
        arrayList = if(prefData?.isEmpty()!!){
            mutableListOf<CityInfoResponse>()
        } else{
            gson.fromJson(prefData,type)
        }

        if(country!= null && lat != null && lon != null && city != null){
            if(arrayList.isEmpty()){
                arrayList.add(CityInfoResponse(country!!,lat!!,lon!!,city!!))
            }
            if(arrayList[0].name != city){
                arrayList[0] = CityInfoResponse(country!!,lat!!,lon!!,city!!)
            }
        }
        Log.d("lat",lat.toString())
        Log.d("lon",lon.toString())
        Log.d("city",city.toString())
        Log.d("country",country.toString())

        Log.d("ArraylistFetched",arrayList.toString())
        val json = gson.toJson(arrayList)
        editor.putString("CityList3", json).apply()

        val pageAdapter : FragmentPagerAdapter = FragmentPagerAdapter(supportFragmentManager,this,arrayList)
        ViewPagerScreen.adapter = pageAdapter
    }

    private fun lastLocation(){
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(!gpsStatus){
            AlertDialog.Builder(this)
                .setTitle("Turn ON GPS")
                .setMessage("Press Yes to turn on GPS")
                .setPositiveButton("YES"){_,_ ->
                    val intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent1);
                }
                .setCancelable(false)
                .create()
                .show()
        }else{
            progressDialog = ProgressDialog(this)
            progressDialog.max = 100
            progressDialog.setMessage("Loading....")
            progressDialog.setTitle("Fetching Weather Data")
            progressDialog.show()
        }

        val task :Task<Location> = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askPermission()
        }
        task.addOnSuccessListener{
            if(it != null){
                lat = it.latitude
                lon = it.longitude
                val address : MutableList<Address> = geocoder.getFromLocation(lat!!,lon!!,1)
                city = address[0].locality
                country = address[0].countryCode
                Log.d("lat2",lat.toString())
                Log.d("lon2",lon.toString())
                Log.d("city2",city.toString())
                Log.d("country2",country.toString())
                sharedPrefCall()
            }
            else{
                LocationServices.getFusedLocationProviderClient(this)
                    .requestLocationUpdates(mLocationRequest, mLocationCallback, null)

            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuSearchbar -> Intent(this,CityListActivity::class.java).also{
                                        startActivityForResult(it, REQUEST_CODE_ADD_CITY)
                                }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("RequestCode",requestCode.toString())
        Log.d("ResultCode",resultCode.toString())
        if(requestCode == REQUEST_CODE_ADD_CITY && resultCode == Activity.RESULT_OK) {
            Log.d("result", resultCode.toString())
            sharedPrefCall()
        }
    }

    override fun onStart() {
        super.onStart()
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            lastLocation()
        }
        else{
            askPermission()
        }
    }

    private fun askPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)
            ){
                ActivityCompat.requestPermissions(
                    this,
                    mutableListOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ).toTypedArray(),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }else{
                ActivityCompat.requestPermissions(
                    this,
                    mutableListOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ).toTypedArray(),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            for(i in grantResults.indices){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    askPermission()
                }
            }
            onStart()
        }
    }
}