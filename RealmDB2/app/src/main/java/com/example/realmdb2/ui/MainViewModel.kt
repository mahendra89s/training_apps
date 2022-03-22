package com.example.realmdb2.ui

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.work.*
import com.example.realmdb2.model.UserDataModel
import com.example.realmdb2.utils.Coroutines
import com.example.realmdb2.utils.DatabaseInitializer
import com.example.realmdb2.utils.DatabaseManager
import com.example.realmdb2.utils.FirebaseWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var nameText : String? = null
    var emailText : String? = null
    var mobileText : String? = null

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer
    @Inject
    lateinit var databaseManager: DatabaseManager
    lateinit var realm: Realm
    lateinit var workManager: WorkManager
    lateinit var  oneTimePeriodicWorkRequest : OneTimeWorkRequest
    var _message = MutableLiveData<String>()


    init {
        pushDataToFireBaseOnInternetConnectivity()
    }
    fun saveUser(view : View){
        if(nameText.isNullOrEmpty() or emailText.isNullOrEmpty() or mobileText.isNullOrEmpty()){
            _message.value = "Error!!"
            return
        }
        viewModelScope.launch {
            databaseManager.saveUser(nameText ?: "",mobileText ?: "",emailText ?: "").collect {
                _message.value = it
            }
        }

//        val user = UserDataModel().apply {
//            this.id = UUID.randomUUID().toString()
//            this.name = nameText
//            this.email = emailText
//            this.mobile = mobileText
//            this.added_to_firebase = false
//        }
//
//        Coroutines.io {
//            val realm = Realm.getDefaultInstance()
//            realm.beginTransaction()
//            realm.copyToRealm(user)
//            Log.d("thread",Thread.currentThread().name)
//            realm.commitTransaction()
//        }


    }

    fun queryRealm(view : View){

        Coroutines.io{
            realm = Realm.getDefaultInstance()
            Log.d("thread3",Thread.currentThread().name)
            val users = realm.where<UserDataModel>().findAll()
            Log.d("users",users.asJSON())
        }
    }

    private fun pushDataToFireBaseOnInternetConnectivity(){
        Log.d("from firebase function","msg")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        oneTimePeriodicWorkRequest = OneTimeWorkRequestBuilder<FirebaseWorkManager>()
            .setConstraints(constraints)
            .build()
        workManager = WorkManager.getInstance()
        workManager.enqueue(oneTimePeriodicWorkRequest)


    }


}