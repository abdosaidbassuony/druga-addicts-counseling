package com.example.drugaddictscounselingsystem.ui.home.chat


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.drugaddictscounselingsystem.R
import com.example.drugaddictscounselingsystem.data.firebase.FireBaseSource
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.example.drugaddictscounselingsystem.utils.startNewMessage
import kotlinx.android.synthetic.main.fragment_chat.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    private lateinit var factory: ChatViewModelFactory
    private lateinit var viewModel: ChatViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val firebase = FireBaseSource()
        val repsitory = UserRepsitory(firebase)
        factory = ChatViewModelFactory(repsitory)
        viewModel = ViewModelProviders.of(this, factory).get(ChatViewModel::class.java)
        viewModel.fetchCurrentUser()

        viewModel.listenForLatestMessages()

        viewModel.adapter.setOnItemClickListener { item, view ->
            val intent = Intent(context, ChatLogActivity::class.java)
            val row = item as LatestMessageItem
            intent.putExtra(NewMessageActivity.USER_KEY, row.partenrUser)
            startActivity(intent)
        }
        chat_fragment_recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        chat_fragment_recycler_view.adapter = viewModel.adapter


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_message_menu -> {
                activity?.startNewMessage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance(): ChatFragment = ChatFragment()
    }


}

