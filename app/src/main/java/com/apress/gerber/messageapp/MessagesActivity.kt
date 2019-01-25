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
        var chatName: String = ""
        var recieverEmail = otherUserEmail
        var re = Regex("[^a-zA-Z0-9 -]")
        val userEmail = re.replace(fbAuth?.currentUser!!.email.toString(), "")

        chatName = if (userEmail < recieverEmail){
            "$userEmail-$recieverEmail"
        }else{
            "$recieverEmail-$userEmail"
        }

        txtChatName.setText(userEmail)

        database = FirebaseDatabase.getInstance().reference
        //var chatDatabase = database.child("messages")

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
                        //messageKey = i.value!!.toInt()
                        //messageKey++
                    }
                    var scrollNum = adapters.adapterSize() - 1
                    recyclerView.scrollToPosition(scrollNum)
                }
            }

        })
        //linearLayoutManager = LinearLayoutManager(this)

        //recyclerView.scrollToPosition(adapter.itemCount - 1)
        //adapter = RecyclerAdapter("Test")

        btnSendMessage.setOnClickListener { view ->
            if (txtMessage.text.toString() != "") {
                database.child("chat").child(chatName).push().setValue(txtMessage.text.toString())
                txtMessage.setText("")
            }
        }

        //txtMessage.requestFocus()
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
