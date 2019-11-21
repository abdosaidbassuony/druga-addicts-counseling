package com.example.drugaddictscounselingsystem.ui.home.chat

import android.content.ContentValues
import android.util.Log
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.model.ChatMessages
import com.example.drugaddictscounselingsystem.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.latest_messages_item.view.*

class LatestMessageItem(val chatMessages: ChatMessages) : Item<GroupieViewHolder>() {
    var partenrUser: User? = null
    override fun getLayout(): Int {
        return R.layout.latest_messages_item
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_txt_latest_messages.text = chatMessages.massageText
        val partnerId: String
        if (chatMessages.fromId == FirebaseAuth.getInstance().uid) {
            partnerId = chatMessages.toId
        } else {
            partnerId = chatMessages.fromId
        }
        val ref = FirebaseDatabase.getInstance().getReference("USER/$partnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                partenrUser = p0.getValue(User::class.java)
                viewHolder.itemView.user_name_latest_messages.text = partenrUser?.name
                val target = viewHolder.itemView.user_image_latest_messages
                Picasso.get()
                        .load(partenrUser?.photoUri)
                        .error(R.drawable.ic_user)
                        .placeholder(R.drawable.ic_user)
                        .into(target)
            }
        })
    }
}