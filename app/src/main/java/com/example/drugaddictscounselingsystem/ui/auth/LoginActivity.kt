package com.example.drugaddictscounselingsystem.activities.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.databinding.ActivityLoginBinding
import com.example.drugaddictscounselingsystem.ui.auth.AuthViewModelFactory
import com.example.drugaddictscounselingsystem.utils.startHomeActivity

class LoginActivity : AppCompatActivity(), AuthListener {


    private val firebase = FireBaseSource()
    private val repsitory = UserRepsitory(firebase)
    private val factory = AuthViewModelFactory(repository = repsitory)


    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authlistener = this
    }

    override fun onStarted() {
        //  progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        //  progressbar.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        //  progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}

