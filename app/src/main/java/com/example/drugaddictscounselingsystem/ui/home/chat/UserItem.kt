package com.example.drugaddictscounselingsystem.ui.home.chat

import android.annotation.SuppressLint
import android.net.Uri
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.new_message_item.view.*


class UserItem(val user: User?) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.new_message_item
    }

    @SuppressLint("ResourceType")
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.name_of_user_new_message.text = user?.name
        Picasso.get()
                .load(Uri.parse(user?.photoUri))
                .error(R.drawable.ic_user)
                .into(viewHolder.itemView.image_of_user_new_message)


    }
}