package com.example.realmdb2.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.realmdb2.model.UserDataModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class FirebaseWorkManager @Inject constructor(
    context : Context,
    workParams : WorkerParameters,
) : CoroutineWorker(context,workParams) {
    //    @Inject
//    lateinit var databaseInitializer: DatabaseInitializer
    lateinit var realm: Realm
    lateinit var databaseReference: DatabaseReference
    lateinit var outputData : Data
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("thread", Thread.currentThread().name)
                realm = Realm.getDefaultInstance()
                val users = realm.where<UserDataModel>().findAll()
                for (i in users) {
                    databaseReference = FirebaseDatabase.getInstance().reference
                    if (!i.added_to_firebase) {
                        Log.d("thread2", Thread.currentThread().name)
                        val user = UserDataModel(
                            id = i.id,
                            name = i.name,
                            email = i.email,
                            mobile = i.mobile,
                            added_to_firebase = true
                        )
                        databaseReference.child("users").child(i.id!!).setValue(user)
                        realm.beginTransaction()
                        realm.copyToRealmOrUpdate(user)
                        realm.commitTransaction()

                    }
                }
                outputData = Data.Builder().putString("status", "success").build()
                Result.success(outputData)
            } catch (e: Throwable) {
                outputData = Data.Builder().putString("status", "fail").build()
                Result.failure(outputData)
            }
        }
    }
}
