package com.example.drugaddictscounselingsystem.ui.home.profile

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.model.User
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.startLoginActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class ProfileViewModel(
        private val repsitory: UserRepsitory
) : ViewModel() {
    lateinit var view: View

    var name: String? = null
    var uri: String? = null
    val currentUser by lazy {
        repsitory.currentUser()
    }

    companion object {
        var user: User? = null
    }

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("USER")


    fun logout(view: View) {
        repsitory.logOut()
        view.context.startLoginActivity()
    }

    fun readFrmData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    Log.d("from read data ", it.toString())
                    val user = it.getValue(User::class.java)

                    Log.d("show data", user.toString())

                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        database.addListenerForSingleValueEvent(postListener)

    }

    fun fetchCurrentUser(textView: TextView, imageView: ImageView) {
        val uid = currentUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("USER").child(uid!!)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                user = p0.getValue(User::class.java)
                textView.text = user?.name
                Picasso.get().load(user?.photoUri).into(imageView)

            }

        })
    }












}