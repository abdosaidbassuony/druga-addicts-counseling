package com.example.drugaddictscounselingsystem.ui.home.chat

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.drugaddictscounselingsystem.data.model.ChatMessages
import com.example.drugaddictscounselingsystem.data.model.User
import com.example.drugaddictscounselingsystem.data.repositories.UserRepsitory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatViewModel(
        private val repsitory: UserRepsitory
) : ViewModel() {

    val currentuser by lazy {
        repsitory.currentUser()
    }

    companion object {
        var user: User? = null
    }

    private var database: DatabaseReference? = null
    val adapter = GroupAdapter<GroupieViewHolder>()
    var fromId: String? = null
    var toId: String? = null
    var messageText: String? = null
    var messageTime: Long? = null
    val latestMaessageHashMap = HashMap<String, ChatMessages>()


    fun readFrmData() {
        database = FirebaseDatabase.getInstance().getReference("USER")

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                    Log.d("from read data ", it.toString())
                    val users = it.getValue(User::class.java)
                    adapter.add(UserItem(users))
                }


            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }


        database!!.addListenerForSingleValueEvent(postListener)

    }

    fun fetchCurrentUser() {
        val uid = currentuser?.uid
        database = FirebaseDatabase.getInstance().getReference("USER/$uid")
        database!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                user = p0.getValue(User::class.java)
                Log.d("fromReadData", user?.name!!)
            }

        })
    }


    fun performSendMesssages(toId: String) {


        fromId = currentuser?.uid
        database = FirebaseDatabase.getInstance().getReference("USER-MESSAGE").child(fromId!!).child(toId).push()
        val toRef = FirebaseDatabase.getInstance().getReference("USER-MESSAGE").child(toId).child(fromId!!).push()
        val chatMessage = ChatMessages(database!!.key!!, fromId!!, messageText!!, toId, System.currentTimeMillis() / 1000)
        database!!.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d("chatMessage", database!!.key!!)
                }
        toRef.setValue(chatMessage)
        val latestMessagesRef = FirebaseDatabase.getInstance().getReference("LATEST-MESSAGES").child(fromId!!).child(toId)
        val latestMessagesToRef = FirebaseDatabase.getInstance().getReference("LATEST-MESSAGES").child(toId).child(fromId!!)
        latestMessagesRef.setValue(chatMessage)
        latestMessagesToRef.setValue(chatMessage)


    }

    fun listenForMessages(toUser: User, currentUser: User) {
        database = FirebaseDatabase.getInstance().getReference("USER-MESSAGE").child(currentUser.id).child(toUser.id)

        database!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessages::class.java)
                if (chatMessage != null) {
                    Log.d("chatLog", chatMessage.massageText)
                    if (chatMessage.fromId == currentuser?.uid) {
                        adapter.add(ChatFromItem(chatMessage.massageText, currentUser))
                    } else {
                        adapter.add(ChatToItem(chatMessage.massageText, toUser))
                    }

                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }


    fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("LATEST-MESSAGES").child(fromId!!)
        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessages = p0.getValue(ChatMessages::class.java) ?: return
                latestMaessageHashMap[p0.key!!] = chatMessages
                refreshRecyclerView()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessages = p0.getValue(ChatMessages::class.java) ?: return
                latestMaessageHashMap[p0.key!!] = chatMessages
                refreshRecyclerView()

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })

    }

    private fun refreshRecyclerView() {
        adapter.clear()
        latestMaessageHashMap.values.forEach {
            adapter.add(LatestMessageItem(it))
        }
    }


}