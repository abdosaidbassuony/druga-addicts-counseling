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
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name_in_user_profile.text = viewModel.name
        print(viewModel.name)
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onStart() {
        super.onStart()
        viewModel.readFrmData()
    }


}
