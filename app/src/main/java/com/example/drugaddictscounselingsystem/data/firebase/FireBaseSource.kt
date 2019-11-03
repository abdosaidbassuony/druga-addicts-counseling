package com.example.drugaddictscounselingsystem.data.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable


class FireBaseSource {
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var context: Context
    val firebaseDatabase = FirebaseDatabase.getInstance()

    fun logIn(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {

                        emitter.onError(task.exception!!)
                    }

                }
    }


    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception!!)
                    }
                }
    }

    fun addTODataBase(any: Any) = Completable.create { emitter ->
        firebaseDatabase.getReference("USER")
                .child(firebaseAuth.currentUser!!.uid)
                .setValue(any)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(task.exception!!)
                    }

                }


    }


    fun logOut() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser


}