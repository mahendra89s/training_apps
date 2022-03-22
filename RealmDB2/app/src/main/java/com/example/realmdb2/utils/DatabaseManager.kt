package com.example.realmdb2.utils

import com.example.realmdb2.model.UserDataModel
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class DatabaseManager @Inject constructor() {

    fun saveUser(name: String,mobile: String,email: String) : Flow<String> =  flow {
        val user = createUserModel(name, mobile, email)
        val message = saveUserToRealmDatabase(user)
        emit(message)
    }


    private fun createUserModel(name: String, mobile: String, email: String): UserDataModel {
        return UserDataModel().apply {
            id = UUID.randomUUID().toString()
            this.name = name
            this.email = email
            this.mobile = mobile
            added_to_firebase = false
        }
    }

    private fun saveUserToRealmDatabase(user : UserDataModel) : String{
        var messageString : String? = null
        messageString = try {
            Coroutines.io {
                val realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                realm.copyToRealm(user)
                realm.commitTransaction()
            }
            "User Added Successfully"
        }catch (e : Exception){
            "Error while Adding User!!!"
        }
        return messageString!!
    }
}