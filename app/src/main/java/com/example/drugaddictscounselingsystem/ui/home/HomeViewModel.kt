package com.example.drugaddictscounselingsystem.ui.home

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.startLoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeViewModel(
        private val repository: UserRepsitory
) : ViewModel() {
    lateinit var view: View
    val toolbar: Toolbar? = null
    val bottomNavigationView: BottomNavigationView? = null


    val user by lazy {
        repository.currentUser()
    }

    fun logout() {
        repository.logOut()
        view.context.startLoginActivity()
    }

    /* @BindingAdapter("app:onItemSelected" )
        fun onItemSelected() {

         val onBottomNavigationView= BottomNavigationView.OnNavigationItemSelectedListener {item ->
              when(item.itemId){
                  R.id.nav_home ->{
                      toolbar?.title = "Home"
                      val homeFragment = HomeFragment.newInstance()
                      openFragment(homeFragment)
                      return@OnNavigationItemSelectedListener true
                  }
                  R.id.nav_chat ->{
                      toolbar?.title = "Chat"
                      val chatFragment = ChatFragment.newInstance()
                      openFragment(chatFragment)
                      return@OnNavigationItemSelectedListener true
                  }
                  R.id.nav_profile ->{
                      toolbar?.title = "Profile"
                      val profilFragment = ProfileFragment.newInstance()
                      openFragment(profilFragment)
                      return@OnNavigationItemSelectedListener true
                  }

              }
              false

          }

          bottomNavigationView?.setOnNavigationItemSelectedListener(onBottomNavigationView)

      }
       private fun openFragment(fragment: Fragment) {
          val fragmentActivity =FragmentActivity()
          val transaction = fragmentActivity.supportFragmentManager.beginTransaction()
          transaction.replace(R.id.container, fragment)
          transaction.addToBackStack(null)
          transaction.commit()
      }*/
}