package com.example.drugaddictscounselingsystem.activities.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}