package com.example.chatapplication.ui.chatScreen

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityChatScreenBinding
import kotlinx.android.synthetic.main.activity_chat_screen.*

class ChatScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat_screen)
        val receiver = intent.getStringExtra("mobile").toString()
        val sender = com.example.chatapplication.utils.SharedPreferences.getInstance(this).accessKey.toString()

        val binding : ActivityChatScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_chat_screen)
        val viewModel = ViewModelProvider(this,ChatScreenViewModelFactory(
            receiver,this
        ))[ChatScreenViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.getLiveData().observe(this,{
            rv_messgaes.apply {
                this.layoutManager = LinearLayoutManager(this@ChatScreenActivity)
                if(it!= null){
                    this.adapter = ChatScreenMessageAdapter(it,this@ChatScreenActivity)
                }
            }
        })
        viewModel.getUser().observe(this,{
            binding.txtName.text = it
        })
        btnBack.setOnClickListener {
            onBackPressed()
        }

    }
}