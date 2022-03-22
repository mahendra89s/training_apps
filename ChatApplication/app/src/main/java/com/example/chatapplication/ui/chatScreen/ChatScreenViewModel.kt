package com.example.chatapplication.ui.chatScreen

import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.data.model.Message
import com.example.chatapplication.utils.SharedPreferences
import com.google.firebase.database.*
import java.sql.Timestamp

class ChatScreenViewModel(
    var receiver: String,
    val context: Context
): ViewModel() {
    var _rName = MutableLiveData<String>()
    val rName : LiveData<String>
        get() = _rName

    lateinit var listener: ValueEventListener
    var message = ObservableField<String>()
    var messages = mutableListOf<Message>()
    var _messages = MutableLiveData<List<Message>>()
    val liveDataMessages : LiveData<List<Message>>
        get() = _messages
    val sender : String = SharedPreferences.getInstance(context).accessKey.toString()
    private var database : DatabaseReference = FirebaseDatabase.getInstance().reference

    init{
        getUserName()
        fetchMessages()
    }

    fun getUserName(){
        database.child("users").child(receiver).get().addOnSuccessListener {
            _rName.value = it.child("name").value.toString()
//            Log.d("rName",rName.toString())
        }
    }
    fun getUser() : LiveData<String> = _rName
    fun sendMsg(view : View){
//        Log.d("message",message.get().toString())
        if(message.get().toString().isEmpty()){
           return
        }
        database = FirebaseDatabase.getInstance().reference
        val sender : String = SharedPreferences.getInstance(context).accessKey.toString()

        val time = System.currentTimeMillis()
//        val timestamp = Timestamp(time).toString()

        val messageJson = Message(sender,receiver,time,message.get().toString())
        database.child("messages").push().setValue(messageJson)
        message.set("")
    }

    fun fetchMessages(){
        listener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                _messages.value = null
                messages.clear()
                for (i in snapshot.child("messages").children){
                    if((i.child("sender").value == sender || i.child("sender").value == receiver)
                and ((i.child("receiver").value == sender || i.child("receiver").value == receiver))
                ){
                    val sender = i.child("sender").value.toString()
                    val receiver = i.child("receiver").value.toString()
                    val message = i.child("message").value.toString()
                    val time = i.child("timeStamp").value.toString().toLong()
                    messages.add(Message(sender,receiver,time,message))
                }
            }
            Log.d("messages",messages.toString())
            _messages.value = messages
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(listener)
//        database.child("messages").get().addOnSuccessListener {
//            for(i in it.children){
//                if((i.child("sender").value == sender || i.child("sender").value == receiver)
//                and ((i.child("receiver").value == sender || i.child("receiver").value == receiver))
//                ){
//                    val sender = i.child("sender").value.toString()
//                    val receiver = i.child("receiver").value.toString()
//                    val message = i.child("message").value.toString()
//                    val time = i.child("timeStamp").value.toString().toLong()
//                    messages.add(Message(sender,receiver,time,message))
//                }
//            }
//            Log.d("messages",messages.toString())
//            _messages.value = messages
//        }
    }

    fun getLiveData() : LiveData<List<Message>> = liveDataMessages

}