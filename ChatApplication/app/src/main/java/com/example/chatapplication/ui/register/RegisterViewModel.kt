package com.example.chatapplication.ui.register

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.chatapplication.data.model.User
import com.example.chatapplication.ui.chatList.ChatListActivity
import com.example.chatapplication.utils.SharedPreferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterViewModel(
    val context : Context
) : ViewModel() {
     var name : String? = null
     var email : String? = null
     var mobile : String? = null
     var password : String? = null

    private lateinit var database : DatabaseReference

    fun onRegisterButtonClicked(view: View) {
        Log.d("name",name.toString())
            if(name.isNullOrEmpty() or email.isNullOrEmpty() or mobile.isNullOrEmpty() or password.isNullOrEmpty()){
                Log.d("xyz","error")
                return
            }
            Log.d("xyz","No error")
            writeNewUserToFirebase(name!!, email!!, mobile!!, password!!)

    }

    fun writeNewUserToFirebase(name: String,email : String,mobile : String,password : String){
        database = FirebaseDatabase.getInstance().reference

        val user = User(name,email,mobile, password,"active")
//        Log.d("register",database.child("users").child(mobile).get().isSuccessful.toString())

        if(!database.child("users").child(mobile).get().isSuccessful){
            database.child("users").child(mobile).setValue(user)
            SharedPreferences.getInstance(context).saveAccessKey(mobile)
            Intent(context,ChatListActivity::class.java).also{
                context.startActivity(it)
            }
        }
        else{
            Toast.makeText(context,"User Already Exists!!!",Toast.LENGTH_LONG).show()
        }


    }

}