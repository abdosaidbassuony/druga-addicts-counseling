package com.example.drugaddictscounselingsystem.data.repositories

import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource

class UserRepsitory(val firebase: FireBaseSource) {
    fun logIn(email: String, password: String) = firebase.logIn(email, password)
    fun register(email: String, password: String) = firebase.register(email, password)
    fun logOut() = firebase.logOut()
    fun currentUser() = firebase.currentUser()
    fun addTODataBase(any: Any) = firebase.addTODataBase(any)
}