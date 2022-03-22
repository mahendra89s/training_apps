package com.example.chatapplication.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.chatapplication.ui.chatList.ChatListActivity
import com.example.chatapplication.utils.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainViewModel (
    val context : Context
) : ViewModel() {
    var mobile : String? = null
    var password : String? = null

    private lateinit var database : DatabaseReference
    fun onLoginButtonClick(view : View){
        if(mobile.isNullOrEmpty() or password.isNullOrEmpty()){
            return
        }
        database = FirebaseDatabase.getInstance().reference
        checkLoginFromFireBase(mobile!!,password!!)
    }

    fun checkLoginFromFireBase(mobile : String, password : String){
        database.child("users").child(mobile).get().addOnSuccessListener {

            if(it.child("password").value.toString() == password){
                Toast.makeText(context,"Successfully Logged In !!!",Toast.LENGTH_LONG).show()
                val mobile : String = it.child("mobile").value.toString()
                SharedPreferences.getInstance(context).saveAccessKey(mobile)
                Intent(context, ChatListActivity::class.java).also{ intent ->
                    context.startActivity(intent)
                }
            }
            else{
                Toast.makeText(context,"Incorrect Login Credentials!!",Toast.LENGTH_LONG).show()
            }
        }
    }
}