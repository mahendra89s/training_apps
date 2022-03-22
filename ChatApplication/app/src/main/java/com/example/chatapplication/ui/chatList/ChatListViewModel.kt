package com.example.chatapplication.ui.chatList

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.data.model.MessageList
import com.example.chatapplication.utils.SharedPreferences
import com.google.firebase.database.*

class ChatListViewModel(
    val context: Context
) : ViewModel() {
    lateinit var database: DatabaseReference
    lateinit var listener: ValueEventListener
    var messageList = MutableLiveData<List<MessageList>>()
    var messageLists = mutableListOf<MessageList>()
    val messages : LiveData<List<MessageList>>
        get() = messageList
    init {
        fetchAllChats()
    }

    fun fetchAllChats() : Boolean{
        database = FirebaseDatabase.getInstance().reference

        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.value = null
                messageLists.clear()
                for (i in snapshot.child("users").children) {
//                    Log.d("users", i.child("mobile").value.toString())
                    if(i.child("mobile").value.toString() != SharedPreferences.getInstance(context).accessKey){
                        val name = i.child("name").value.toString()
                        val mobile = i.child("mobile").value.toString()
                        val status = i.child("status").value.toString()
                        messageLists.add(MessageList(name,mobile,"",0,status))
                    }
                }
                Log.d("messageLits",messageLists.toString())
                messageList.value = messageLists
                Log.d("messageList",messageList.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(listener)
        return true
    }


    fun getChats() : LiveData<List<MessageList>> = messages


    fun changeStatus(message : String){
        database = FirebaseDatabase.getInstance().reference
        val user = SharedPreferences.getInstance(context).accessKey.toString()
        database.child("users").child(user).child("status").setValue(message)
    }
}
