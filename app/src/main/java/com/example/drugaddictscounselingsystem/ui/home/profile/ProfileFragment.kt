package com.example.drugaddictscounselingsystem.ui.home.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private val firebase = FireBaseSource()
    private val repsitory = UserRepsitory(firebase)
    private val factory = ProfileViewModelFactory(repsitory)
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val text = name_in_user_profile
        val image = image_profile
        viewModel.fetchCurrentUser(text, image)


        val target = image_profile
        Picasso.get().load(ProfileViewModel.user?.photoUri).error(R.drawable.ic_user).into(target)
    }


    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }


}


