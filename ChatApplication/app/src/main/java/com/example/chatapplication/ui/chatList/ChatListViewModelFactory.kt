package com.example.chatapplication.ui.chatList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatListViewModelFactory(
    val context: Context
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatListViewModel(context) as T
    }
}