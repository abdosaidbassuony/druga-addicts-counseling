package com.example.drugaddictscounselingsystem.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory

class ProfileViewModelFactory(
        private val repsitory: UserRepsitory
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repsitory = repsitory) as T
    }
}