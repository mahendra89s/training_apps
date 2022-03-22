package com.example.realmdb2.utils

import io.realm.Realm
import io.realm.RealmConfiguration

class DatabaseInitializer {
//    lateinit var realm: Realm
    init {
        Coroutines.io {
            val config = RealmConfiguration.Builder()
                .name("users.db")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build()
            Realm.setDefaultConfiguration(config)
//            var realm = Realm.getInstance(config)
        }

    }

}