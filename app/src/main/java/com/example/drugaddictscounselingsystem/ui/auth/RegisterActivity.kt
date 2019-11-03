package com.example.drugaddictscounselingsystem.activities.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.databinding.ActivityRegisterBinding
import com.example.drugaddictscounselingsystem.ui.auth.AuthViewModelFactory
import com.example.drugaddictscounselingsystem.ui.home.HomeActivity
import com.example.drugaddictscounselingsystem.utils.startHomeActivity

class SignupActivity : AppCompatActivity(), AuthListener {
    private val firebase = FireBaseSource()
    private val repsitory = UserRepsitory(firebase)
    private val factory = AuthViewModelFactory(repository = repsitory)

    // override val kodein by kodein
    //  private val factory : AuthViewModelFactory by instance()

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.authlistener = this
    }

    override fun onStarted() {
        // progressbar.visibility = View.VISIBLE
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSuccess() {
        //   progressbar.visibility = View.GONE

        startHomeActivity()
        viewModel.writeTodataBase()

    }

    override fun onFailure(message: String) {
        //  progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
