package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : AppCompatActivity() {

    //private var messageList: ArrayList<Photo> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        val testMessages: ArrayList<String> = ArrayList()

        for (i in 1..100)
        {
            testMessages.add("Message #$i")
        }

        //linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UsersAdapter(testMessages)

        //adapter = RecyclerAdapter("Test")



    }
}
