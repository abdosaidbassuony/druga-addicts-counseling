package com.example.drugaddictscounselingsystem.ui.home.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory

class ChatViewModelFactory(
        private val repsitory: UserRepsitory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(repsitory) as T
    }
}
