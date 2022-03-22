package com.example.mvvmarchitecture.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmarchitecture.data.db.AppDatabase
import com.example.mvvmarchitecture.data.db.entities.User
import com.example.mvvmarchitecture.data.network.MyAPI
import com.example.mvvmarchitecture.data.network.SafeApiRequest
import com.example.mvvmarchitecture.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val api : MyAPI,
    private val db : AppDatabase
) : SafeApiRequest() {
    suspend fun userLogin( email:String , password : String) : AuthResponse {
        return apiRequest {
            api.userLogin(email, password)
        }
    }
    suspend fun userRegister(
        first_name : String,
        last_name : String,
        email: String,
        password: String,
        confirm_password : String,
        gender : String,
        phone_no : String
    ) : AuthResponse{
        return apiRequest {
            api.registerUser(first_name, last_name, email, password, confirm_password, gender, phone_no)
        }
    }
    suspend fun saveUser(user : User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}