package com.example.drugaddictscounselingsystem.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
        private val repository: UserRepsitory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }

}