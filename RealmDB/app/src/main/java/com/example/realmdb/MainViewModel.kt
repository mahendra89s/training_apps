package com.example.realmdb

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    val context: Context
) : ViewModel() {
    var nameText : String? = null
    var emailText : String? = null
    var mobileText : String? = null
    lateinit var realm: Realm
    init {
        createRealmConfiguration()
    }
    fun saveUser(view : View){
        if(nameText.isNullOrEmpty() or emailText.isNullOrEmpty() or mobileText.isNullOrEmpty()){
                return
        }
        val user = UserDataModel().apply {
            this.id = UUID.randomUUID().toString()
            this.name = nameText
            this.email = emailText
            this.mobile = mobileText
        }
        realm.beginTransaction()
        realm.copyToRealm(user)
        realm.commitTransaction()
    }
    fun createRealmConfiguration(){

        val config = RealmConfiguration.Builder()
            .name("users.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getInstance(config)
    }


}