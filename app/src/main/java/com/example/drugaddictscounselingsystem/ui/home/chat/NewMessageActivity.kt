package com.example.drugaddictscounselingsystem.ui.home.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {
    private lateinit var factory: ChatViewModelFactory
    private lateinit var viewModel: ChatViewModel

    companion object {
        val USER_KEY = "USER_KEY"
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        window.statusBarColor = Color.parseColor("#00574B")
        val firebase = FireBaseSource()
        val repsitory = UserRepsitory(firebase)
        factory = ChatViewModelFactory(repsitory)
        viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)
        viewModel.readFrmData()
        new_message_recycle_view.layoutManager = LinearLayoutManager(this)
        new_message_recycle_view.adapter = viewModel.adapter
        viewModel.fetchCurrentUser()


        viewModel.adapter.setOnItemClickListener { item, view ->
            val userItem = item as UserItem
            val intent = Intent(view.context, ChatLogActivity::class.java)
            intent.putExtra(USER_KEY, userItem.user)
            view.context.startActivity(intent)
            finish()


        }
    }
}
