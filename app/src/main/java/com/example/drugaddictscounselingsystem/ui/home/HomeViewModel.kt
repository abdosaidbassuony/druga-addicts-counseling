package com.example.drugaddictscounselingsystem.ui.home

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.model.Post
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class HomeViewModel(
        private val repository: UserRepsitory
) : ViewModel() {
    lateinit var view: View
    val toolbar: Toolbar? = null
    val bottomNavigationView: BottomNavigationView? = null
    var postText: String? = null
    val adapter = GroupAdapter<GroupieViewHolder>()
    val readPostsHashMap = HashMap<String, Post>()




    val user by lazy {
        repository.currentUser()
    }
    val uid = user?.uid

    fun writePost() {

        val ref = FirebaseDatabase.getInstance().getReference("POSTS").child(uid!!).push()
        val post = Post(ref.key.toString(), uid, postText!!)
        ref.setValue(post)
    }

    fun readPosts() {
        val ref = FirebaseDatabase.getInstance().getReference("POSTS").child(uid!!)
        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val post = p0.getValue(Post::class.java) ?: return
                readPostsHashMap[p0.key!!] = post
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val post = p0.getValue(Post::class.java) ?: return
                readPostsHashMap[p0.key!!] = post
                refreshRecyclerView()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }

    private fun refreshRecyclerView() {
        adapter.clear()
        readPostsHashMap.values.forEach {
            adapter.add(PostItem(it))
        }
    }


}