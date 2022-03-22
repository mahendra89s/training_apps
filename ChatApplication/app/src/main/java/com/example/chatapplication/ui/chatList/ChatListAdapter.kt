package com.example.chatapplication.ui.chatList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.data.model.MessageList
import com.example.chatapplication.databinding.ChatListLayoutBinding

class ChatListAdapter(
    val chats : List<MessageList>,
    val listener: ChatClickListener,
    val context: Context
) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding : ChatListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.chat_list_layout,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ChatListAdapter.ViewHolder, position: Int) {
        holder.binding.chat = chats[position]
        if(chats[position].status == "active"){
            holder.binding.txtStatus.setTextColor(context.resources.getColor(R.color.green))
        }
        else{
            holder.binding.txtStatus.setTextColor(context.resources.getColor(R.color.red))
        }
        holder.binding.chatLayout.setOnClickListener {
            listener.onClick(chats[position].number)
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }
}