package com.example.drugaddictscounselingsystem.activities.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.databinding.ActivityRegisterBinding
import com.example.drugaddictscounselingsystem.ui.auth.AuthViewModelFactory
import com.example.drugaddictscounselingsystem.utils.startHomeActivity
import kotlinx.android.synthetic.main.activity_register.*

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

        add_image_profile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.photoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, viewModel.photoUri)
            image_profile.setImageBitmap(bitmap)


        }
    }



    override fun onStarted() {
        // progressbar.visibility = View.VISIBLE

    }

    override fun onSuccess() {
        //   progressbar.visibility = View.GONE

        startHomeActivity()


    }

    override fun onFailure(message: String) {
        //  progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
