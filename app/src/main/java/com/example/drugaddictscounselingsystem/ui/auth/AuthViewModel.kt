package com.example.drugaddictscounselingsystem.activities.ui.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.model.User
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class AuthViewModel(private val reprsitory: UserRepsitory) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    var photoUri: Uri? = null
    val context: Context? = null
    var authlistener: AuthListener? = null
    var firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()


    private val disposables = CompositeDisposable()
    val user by lazy {
        reprsitory.currentUser()
    }


    fun onLoginButtonClick(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authlistener?.onFailure("Invalid email or password ")
            return
        }
        authlistener?.onStarted()
        val disposable = reprsitory.logIn(email!!, password!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    authlistener?.onSuccess()

                }, {
                    authlistener?.onFailure(it.message!!)


                }
                )

        disposables.add(disposable)

    }

    fun onRegisterButtonClick(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || password!!.length < 6) {
            authlistener?.onFailure("Invalid email or password ")
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("form register ", "email = $email password = $password")
                uploadImageToStorage()


                authlistener?.onSuccess()


            } else {
                it.exception
            }
        }

//

    }


    fun writeTodataBase(photoUri: String) {

        val userModel = User(user!!.uid, name!!, photoUri)

        //  myRef.child(userid).setValue(model)
        firebaseDatabase.getReference("USER")
                .child(firebaseAuth.currentUser!!.uid)
                .setValue(userModel)

//        val disposable =reprsitory.addTODataBase(userModel)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    authlistener?.onSuccess()
//
//
//                },{
//                    authlistener?.onFailure(it.message!!)
//
//                }
//                )
//       disposables.add(disposable)
    }

    fun fetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("USER").child(firebaseAuth.currentUser!!.uid)

    }




    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToSignup(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun uploadImageToStorage() {

        val fileName = UUID.randomUUID().toString()
        val reference = FirebaseStorage.getInstance().getReference("/images/$fileName")
        reference.putFile(photoUri!!)
                .addOnSuccessListener {
                    Log.d("from upload image", "Successfully upload image ${it.metadata?.path} ")

                }
                .addOnCompleteListener {
                    reference.downloadUrl
                            .addOnSuccessListener {
                                Log.d("from upload image", "uri is $it")
                                photoUri = it
                                Log.d("photoUri is ", photoUri.toString())
                                writeTodataBase(photoUri.toString())
                                Log.d("added to data base", photoUri.toString())


                            }


                }
    }


    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }


}