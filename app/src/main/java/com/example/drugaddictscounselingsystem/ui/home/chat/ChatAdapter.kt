package com.example.drugaddictscounselingsystem.ui.home.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.model.User

import com.example.drugaddictscounselingsystem.databinding.NewMessageItemBinding

class ChatAdapter(
        private val user: List<User>
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun getItemCount() = user.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ChatViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.new_message_item,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.chatFragmentChatBinding.user = user[position]
    }


    inner class ChatViewHolder(
            val chatFragmentChatBinding: NewMessageItemBinding
    ) : RecyclerView.ViewHolder(chatFragmentChatBinding.root)
}




