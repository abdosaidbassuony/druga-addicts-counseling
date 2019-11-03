package com.example.drugaddictscounselingsystem.ui.home.profile

import android.content.ContentValues
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.startLoginActivity
import com.google.firebase.database.*


class ProfileViewModel(
        private val repsitory: UserRepsitory
) : ViewModel() {
    lateinit var view: View

    var name: String? = null
    val user by lazy {
        repsitory.currentUser()
    }
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(user!!.uid)


    fun logout(view: View) {
        repsitory.logOut()
        view.context.startLoginActivity()
    }

    fun readFrmData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val map = dataSnapshot.value as Map<String, Any>
                // ...
                name = map.getValue("name").toString()

                Log.d("abdo", name!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        database.addListenerForSingleValueEvent(postListener)

    }


}