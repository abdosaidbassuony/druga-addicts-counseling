package com.example.drugaddictscounselingsystem.data.firebase

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import java.util.*


class FireBaseSource {
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var context: Context
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val firebaseStorage = FirebaseStorage.getInstance()

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

    fun addToStorage(photoUri: Uri) = Completable.create { emitter ->

        val fileName = UUID.randomUUID().toString()
        val reference = FirebaseStorage.getInstance().getReference("/images/$fileName")
        reference.putFile(photoUri)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        reference.downloadUrl
                                .addOnCompleteListener {

                                    emitter.onComplete()

                                }
                    } else {
                        emitter.onError(it.exception!!)
                    }
                }
    }


    fun logOut() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser


}