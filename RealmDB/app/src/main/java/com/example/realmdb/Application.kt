package com.example.realmdb

import android.app.Application
import io.realm.Realm

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}