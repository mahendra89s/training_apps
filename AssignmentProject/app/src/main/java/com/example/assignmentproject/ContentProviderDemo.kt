package com.example.assignmentproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_content_provider_demo.*


class ContentProviderDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider_demo)
        requestPermission()
        getContacts()
    }




    @SuppressLint("Range")
    private fun getContacts(){
        val contactList = mutableListOf<String>()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contactList.add(name)
                Log.d("Contact Names", name)
            }
            while(cursor.moveToNext())
            cursor.close()
        }
        autoCompleteTextView.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,contactList))
        autoCompleteTextView.threshold = 1
        autoCompleteTextView.setOnItemClickListener { parent, view, position, Id ->
            Toast.makeText(this,"Selected Contact is ${parent.getItemAtPosition(position)}",Toast.LENGTH_LONG).show()
        }


    }

    private fun checkReadContactPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        var permissionsToRequest = mutableListOf<String>()
        if(!checkReadContactPermission()){
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }
        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionsToRequest.toTypedArray(),0)
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
                    Log.d("Permision","${permissions[i]} granted!!!")
                }
            }
        }
    }
}