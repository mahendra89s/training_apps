package com.example.mvvmarchitecture.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmarchitecture.data.db.entities.User

interface AuthListener {
    fun onSuccess(user: User)
    fun onStarted()
    fun onFailure(message : String)
}