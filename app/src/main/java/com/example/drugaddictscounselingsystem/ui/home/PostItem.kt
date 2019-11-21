package com.example.drugaddictscounselingsystem.ui.home

import android.content.ContentValues
import android.util.Log
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.model.Post
import com.example.drugaddictscounselingsystem.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.hom_posts_item.view.*

class PostItem(val post: Post) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.hom_posts_item

    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.post_txt.text = post.post
        val uid = FirebaseAuth.getInstance().uid
        val red = FirebaseDatabase.getInstance().getReference("USER").child(uid!!)
        red.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", p0.toException())

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)
                viewHolder.itemView.user_name_posts_home.text = user?.name
                val target = viewHolder.itemView.user_profile_post_home
                Picasso.get().load(user?.photoUri).error(R.drawable.ic_user).into(target)
            }

        })


    }
}