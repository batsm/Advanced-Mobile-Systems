package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_messages.*
import java.util.*

class MessagesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter

    var fbAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        database = FirebaseDatabase.getInstance().reference
        val chatMessages: ArrayList<String> = ArrayList()
        var newMessage: String = ""
        var chatName: String = ""
        var receiverEmail = otherUserEmail
        var re = Regex("[^a-zA-Z0-9 -]")
        val userEmail = re.replace(fbAuth?.currentUser!!.email.toString(), "")
        txtChatName.setText(receiverEmail)

        chatName = if (userEmail < receiverEmail){
            "$userEmail-$receiverEmail"
        }else{
            "$receiverEmail-$userEmail"
        }

        val adapters: UsersAdapter by lazy(LazyThreadSafetyMode.NONE) {
            UsersAdapter(chatMessages)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        database.child("chat").child(chatName).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot!!.exists()) {
                    adapters.clearMessages()
                    recyclerView.adapter = UsersAdapter(chatMessages)

                    for (i in dataSnapshot.children) {
                        newMessage = i.value.toString()
                        adapters.addMessage(newMessage)
                    }
                    var scrollNum = adapters.adapterSize() - 1
                    recyclerView.scrollToPosition(scrollNum)
                }
            }

        })

        btnSendMessage.setOnClickListener { view ->
            if (txtMessage.text.toString() != "") {
                database.child("chat").child(chatName).push().setValue(txtMessage.text.toString())
                txtMessage.setText("")
            }
        }
    }
}
