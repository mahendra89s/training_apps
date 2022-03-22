package com.example.neosoftstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.d("TAG", SharedPrefManagerForUserLogReg.getInstance(this).isLogged.toString())
        Handler(Looper.getMainLooper()).postDelayed({
            if(SharedPrefManagerForUserLogReg.getInstance(this).isLogged){
                Intent(this,HomeScreenActivity::class.java).also{
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
            else{
                Intent(this,LoginActivity::class.java).also{
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }

        }, SPLASH_SCREEN_TIME_OUT)
    }

}