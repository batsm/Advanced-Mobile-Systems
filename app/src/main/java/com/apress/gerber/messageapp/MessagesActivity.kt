package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_messages.*
import java.util.*

class MessagesActivity : AppCompatActivity() {

    //private var messageList: ArrayList<Photo> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter

    var fbAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        val chatMessages: ArrayList<String> = ArrayList()
        var messageKey: Int = 0
        var newMessage: String = ""

        database = FirebaseDatabase.getInstance().reference
        var chatDatabase = database.child("messages")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UsersAdapter(chatMessages)

        val adapters: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) {
            UsersAdapter(chatMessages)
        }

        chatDatabase.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot!!.exists()) {
                    for (i in dataSnapshot.children) {
                        newMessage = i.value.toString()
                        //chatMessages.add(newMessage)
                        adapters.addMessage(newMessage)
                        messageKey = i.key!!.toInt()
                        messageKey++
                        //recyclerView.layoutManager = LinearLayoutManager(null)
                        //recyclerView.adapter = UsersAdapter.
                    }
                }
            }

        })
        //linearLayoutManager = LinearLayoutManager(this)
        

        //recyclerView.scrollToPosition(adapter.itemCount - 1)
        //adapter = RecyclerAdapter("Test")

        btnSendMessage.setOnClickListener { view ->
            chatDatabase.child(messageKey.toString()).setValue(txtMessage.text.toString())
            //adapters.addMessage(newMessage)
            //chatMessages.add(txtMessage.text.toString())

            //recyclerView.layoutManager = LinearLayoutManager(this)
            //recyclerView.adapter = UsersAdapter(chatMessages)
            //var temp1 = testMessages.size + 1
            //testMessages[temp1] = txtMessage.text.toString()
            //recyclerView.scrollToPosition(adapter.itemCount)

        }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
