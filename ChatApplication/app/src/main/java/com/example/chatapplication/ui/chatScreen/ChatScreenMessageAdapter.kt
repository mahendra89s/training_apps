package com.example.chatapplication.ui.chatScreen

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.data.model.Message
import com.example.chatapplication.databinding.MessageLayoutBinding
import com.example.chatapplication.utils.SharedPreferences

class ChatScreenMessageAdapter(
    val chats : List<Message>,
    val context: Context
) : RecyclerView.Adapter<ChatScreenMessageAdapter.ViewHolder>(){
    inner class ViewHolder(val binding : MessageLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatScreenMessageAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.message_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatScreenMessageAdapter.ViewHolder, position: Int) {
        holder.binding.binding = chats[position]
        val sender = SharedPreferences.getInstance(context).accessKey
        if(chats[position].sender != sender){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.binding.messageLayout.gravity = Gravity.START
                holder.binding.txtMessage.background = ContextCompat.getDrawable(context,R.drawable.message_layout2)
            }
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

}