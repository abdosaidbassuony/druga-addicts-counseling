package com.example.drugaddictscounselingsystem.ui.home.chat

import android.util.Log
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_raw.view.*

class ChatFromItem(val messageText: String, val user: User) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_raw
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("fromChatItem", user.id)
        Log.d("fromChatItem", user.photoUri)
        Log.d("fromChatItem", user.name)
        viewHolder.itemView.message_txt_from_chat_from_row.text = messageText
        Picasso
                .get()
                .load(user.photoUri)
                .error(R.drawable.ic_user)
                .placeholder(R.drawable.ic_user)
                .into(viewHolder.itemView.user_image_chat_from_rw)
    }
}