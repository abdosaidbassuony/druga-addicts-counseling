package com.example.drugaddictscounselingsystem.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugaddictscounselingsystem.activities.ui.auth.AuthViewModel
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
        private val repository: UserRepsitory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }

}