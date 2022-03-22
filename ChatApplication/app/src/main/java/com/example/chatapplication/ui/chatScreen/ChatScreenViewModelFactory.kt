package com.example.chatapplication.ui.chatScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatScreenViewModelFactory(
    val receiver : String,
    val context : Context
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatScreenViewModel(receiver,context) as T
    }

}