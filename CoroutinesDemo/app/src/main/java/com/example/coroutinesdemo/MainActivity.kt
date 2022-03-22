package com.example.coroutinesdemo

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {

    lateinit var receiver : AirplaneModeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnApply.setOnClickListener{
            val firstname = firstname.text.toString()
            val lastname = lastname.text.toString()
            val dob = dob.text.toString()
            val country = country.text.toString()
            val person = Person(firstname,lastname,dob,country)
            Log.d("Message","$firstname $lastname $dob $country")
            Intent(this,SecondActivity::class.java).also {
//                it.putExtra("EXTRA_FirstName",firstname)
//                it.putExtra("EXTRA_LastName",lastname)
//                it.putExtra("EXTRA_DOB",dob)
//                it.putExtra("EXTRA_Country",country)
                it.putExtra("EXTRA_PERSON",person)
                startActivity(it)
            }
        }
        var count = 0
        counterbtn.setOnClickListener{
            count++
            counterText.text = "Counter : $count"
        }
        btnfragment.setOnClickListener{
            val intent = Intent(this,FragmentActivity::class.java)
            startActivity(intent)
        }
        btnRequest.setOnClickListener {
            requestPermission()
        }
        btnImplicit.setOnClickListener {
            Intent(this,ImplicitIntent::class.java).also{
                startActivity(it)
            }
        }
        btnDialog.setOnClickListener {
            Intent(this,DialogBox::class.java).also{
                startActivity(it)
            }
        }

//        val customList = arrayListOf<String>("First","Second","Third","Fourth")
//        val adapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,customList)
//        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity,"You Clicked on ${adapter?.getItemAtPosition(position).toString()}",Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        btnRecycleView.setOnClickListener {
            Intent(this,RecycleViewActivity::class.java).also{
                startActivity(it)
            }
        }
        btnBottomNav.setOnClickListener {
            Intent(this,BottomNavDemo::class.java).also{
                startActivity(it)
            }
        }
        btnSideNav.setOnClickListener {
            Intent(this,SlidableMenuNavBar::class.java).also{
                startActivity(it)
            }
        }
        receiver = AirplaneModeChangeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also{
            registerReceiver(receiver,it)
        }

        btnwifi.setOnClickListener {
            Intent(this,WIFIActivity::class.java).also{
                startActivity(it)
            }
        }
        btnretrofit.setOnClickListener {
            Intent(this,RetrofitActivity::class.java).also {
                startActivity(it)
            }
        }
    }


//    Start APP PERMISSION
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasBackgroungLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasForegroundLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


    private fun requestPermission(){
        var requestPermission = mutableListOf<String>()
        if(!hasWriteExternalStoragePermission()){
            requestPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasBackgroungLocationPermission()){
            requestPermission.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if(!hasForegroundLocationPermission()){
            requestPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(requestPermission.isNotEmpty()){
            ActivityCompat.requestPermissions(this,requestPermission.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Message","${permissions[i]} granted")
                }
            }
        }
    }
//    End of APP PERMISSION


//    Start of APP BAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> Toast.makeText(this,"Clicked on Settings",Toast.LENGTH_LONG).show()
            R.id.addContact -> Toast.makeText(this,"Clicked on Add Contact",Toast.LENGTH_LONG).show()
            R.id.favorite -> Toast.makeText(this,"Clicked on Favourite",Toast.LENGTH_LONG).show()
            R.id.feedback -> Toast.makeText(this,"Clicked on Feedback",Toast.LENGTH_LONG).show()
            R.id.closeApp -> finish()
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}