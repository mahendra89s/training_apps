package com.example.chatapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(
    val context: Context
) {

    companion object{
        private val SHARED_PREF = "user_number"
        @SuppressLint("StaticFieldLeak")
        private var sharedPreference : com.example.chatapplication.utils.SharedPreferences? = null
        fun getInstance(context: Context) : com.example.chatapplication.utils.SharedPreferences{
            if(sharedPreference == null){
                sharedPreference = SharedPreferences(context)
            }

            return sharedPreference as com.example.chatapplication.utils.SharedPreferences

        }
    }
    val isLoggedIn : Boolean
    get() {
        val sharedPref = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        return sharedPref.getString("user","-1") != "-1"
    }

    val accessKey : String?
    get() {
        val sharedPref = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        return sharedPref.getString("user","-1")
    }

    fun saveAccessKey(accessKey : String){
        val sharedPref = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user",accessKey)
        editor.apply()
    }
    fun clearAccessKey(){
        val sharedPref = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}