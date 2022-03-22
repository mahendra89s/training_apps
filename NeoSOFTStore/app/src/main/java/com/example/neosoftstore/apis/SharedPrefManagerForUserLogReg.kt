package com.example.neosoftstore.apis

import android.content.Context

class SharedPrefManagerForUserLogReg private constructor(val mCtx: Context) {

    companion object{
        private val SHARED_PREF = "user_access_token"
        private var sharedprefInstance : SharedPrefManagerForUserLogReg? = null

        fun getInstance(mCtx: Context): SharedPrefManagerForUserLogReg {
            if (sharedprefInstance == null) {
                sharedprefInstance = SharedPrefManagerForUserLogReg(mCtx)
            }
            return sharedprefInstance as SharedPrefManagerForUserLogReg
        }
    }
    val isLogged : Boolean
    get() {
        val sharedPref = mCtx.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        return sharedPref.getString("access_token","-1") != "-1"
    }

    val accessKey : String?
    get() {
        val sharedPref = mCtx.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE)
        return sharedPref.getString("access_token","-1")
    }

    fun saveAccessKey(accessKey : String){
        val sharedPref = mCtx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("access_token",accessKey)
        editor.apply()
    }

    fun clearAccessKey(){
        val sharedPref = mCtx.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}