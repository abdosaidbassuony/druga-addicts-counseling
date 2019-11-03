package com.example.drugaddictscounselingsystem.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.drugaddictscounselingsystem.activities.ui.auth.LoginActivity
import com.example.drugaddictscounselingsystem.ui.home.HomeActivity

fun Context.toast(massage: String) {
    Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
}

fun Context.startHomeActivity() =
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startLoginActivity() =
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }