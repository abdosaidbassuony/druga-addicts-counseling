package com.example.drugaddictscounselingsystem.ui.home.chat

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.model.User
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.toast
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {
    private lateinit var factory: ChatViewModelFactory
    private lateinit var viewModel: ChatViewModel

    companion object {
        var user: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        window.statusBarColor = Color.parseColor("#00574B")

        val firebase = FireBaseSource()
        val repsitory = UserRepsitory(firebase)
        factory = ChatViewModelFactory(repsitory)
        viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)


        val currentUser = ChatViewModel.user
        val toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        if (toUser != null || currentUser != null) {
            supportActionBar?.title = toUser?.name
            viewModel.listenForMessages(toUser!!, currentUser!!)
        }

        // viewModel.listenForMessages(user.photoUri)

        val toId = toUser?.id
        floatingActionButton_chat_log.setOnClickListener {
            viewModel.messageText = editText_chat_log.text.toString()
            toast(viewModel.messageText!!)
            viewModel.performSendMesssages(toId.toString())
            editText_chat_log.text.clear()
            chat_row_recycle_view.scrollToPosition(viewModel.adapter.itemCount - 1)
        }

        chat_row_recycle_view.adapter = viewModel.adapter

    }

}
