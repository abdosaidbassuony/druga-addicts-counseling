package com.example.drugaddictscounselingsystem.activities.ui.auth

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AuthViewModel(private val reprsitory: UserRepsitory) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    val context: Context? = null
    var authlistener: AuthListener? = null
    var firebaseAuth = FirebaseAuth.getInstance()


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
//            authlistener?.onStarted()
//            val disposable = reprsitory.register(email!!, password!!)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({
//                      authlistener?.onSuccess()
//                    }, {
//
//
//                        authlistener?.onFailure(it.message!!)
//
//                    }
//
//                    )
//
//
//            disposables.add(disposable)

        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener {
            if (it.isSuccessful) {
                writeTodataBase()

                authlistener?.onSuccess()


            } else {
                it.exception
            }
        }

//

    }


    fun writeTodataBase() {


        val currentUserDb: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(user!!.uid)
        val map = mapOf("name" to name)
        currentUserDb.setValue(map)

//         val database = FirebaseDatabase.getInstance()
//         val myRef = database.getReference("USERS")
//
//
//         val userModel=User(userid,"abdo")
//         myRef.child(userid).setValue(userModel)


//        val disposable =reprsitory.addTODataBase(user)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    authlistener?.onSuccess()
//
//                },{
//                    authlistener?.onFailure(it.message!!)
//
//                }
//                )
//        disposables.add(disposable)
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


    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}