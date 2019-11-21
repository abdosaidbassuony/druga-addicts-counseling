package com.example.drugaddictscounselingsystem.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.drugaddictscounselingsystem.activities.ui.auth.LoginActivity
import com.example.drugaddictscounselingsystem.ui.home.AddNewPostActivity
import com.example.drugaddictscounselingsystem.ui.home.HomeActivity
import com.example.drugaddictscounselingsystem.ui.home.chat.NewMessageActivity

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

fun Context.startNewMessage() =
        Intent(this, NewMessageActivity::class.java).also {

            startActivity(it)
        }

fun Context.pickImageButtom() =
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(Activity(), it, 0, Bundle())
        }

fun Context.startAddNewPostActivity() =
        Intent(this, AddNewPostActivity::class.java).also {
            startActivity(it)
        }

