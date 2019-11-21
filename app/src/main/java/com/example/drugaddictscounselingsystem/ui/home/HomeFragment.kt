package com.example.drugaddictscounselingsystem.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.startAddNewPostActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val firebase = FireBaseSource()
        val repsitory = UserRepsitory(firebase)
        factory = HomeViewModelFactory(repsitory)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        add_new_post.setOnClickListener {
            it.context.startAddNewPostActivity()
        }
        home_fragment_recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        home_fragment_recycler_view.adapter = viewModel.adapter
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun onStart() {
        super.onStart()
        viewModel.readPosts()
    }
}
