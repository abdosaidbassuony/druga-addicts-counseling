package com.example.drugaddictscounselingsystem.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import kotlinx.android.synthetic.main.activity_add_new_post.*

class AddNewPostActivity : AppCompatActivity() {
    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_post)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.colorPrimary))
        window.statusBarColor = Color.parseColor("#00574B")
        val firebase = FireBaseSource()
        val repsitory = UserRepsitory(firebase)
        factory = HomeViewModelFactory(repsitory)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_new_post_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_post_menu -> {
                viewModel.postText = post_edit_txt.text.toString()
                viewModel.writePost()
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
