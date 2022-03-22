package com.example.realmdb2

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import javax.inject.Inject

@HiltAndroidApp
class MyApplicationClass : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}