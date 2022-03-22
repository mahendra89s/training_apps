package com.example.chatapplication.ui.chatList

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityChatListBinding
import com.example.chatapplication.ui.chatScreen.ChatScreenActivity
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() , ChatClickListener {
    lateinit var viewModel : ChatListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityChatListBinding = DataBindingUtil.setContentView(this,R.layout.activity_chat_list)
        viewModel = ViewModelProvider(
            this,
            ChatListViewModelFactory(this))[ChatListViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.fetchAllChats()

        viewModel.getChats().observe(this,{
            rvChatList.layoutManager = LinearLayoutManager(this)
            if(it != null){
                rvChatList.adapter = ChatListAdapter(it,this,this)
            }

        })
    }

    override fun onClick(mobile: String) {
        Intent(this,ChatScreenActivity::class.java).also {
            it.putExtra("mobile",mobile)
            startActivity(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.changeStatus("active")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.changeStatus("inactive")
    }

}