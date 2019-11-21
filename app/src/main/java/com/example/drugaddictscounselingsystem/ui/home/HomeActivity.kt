package com.example.drugaddictscounselingsystem.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.ui.home.chat.ChatFragment
import com.example.drugaddictscounselingsystem.ui.home.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val firebase = FireBaseSource()
    private val repsitory = UserRepsitory(firebase)
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomNavigationView?.setOnNavigationItemSelectedListener(onBottomNavigationView)


    }

    private val onBottomNavigationView = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                toolbar?.title = "Home"

                val homeFragment = HomeFragment.newInstance()

                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_chat -> {
                toolbar?.title = "Chat"
                val chatFragment = ChatFragment.newInstance()

                openFragment(chatFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                toolbar?.title = "Profile"
                val profilFragment = ProfileFragment.newInstance()

                openFragment(profilFragment)
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.home_page_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.sign_out -> {
//                viewModel.logout()
//                startLoginActivity()
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}






