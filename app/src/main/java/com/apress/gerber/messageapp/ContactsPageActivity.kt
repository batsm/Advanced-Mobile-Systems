package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_contacts_page.*

class ContactsPageActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_page)

        val user = FirebaseAuth.getInstance().currentUser

        txtContactText.text = FirebaseAuth.getInstance().uid

        btnAddContactsPage.setOnClickListener { view ->
            var intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }

        btnMessagesPage.setOnClickListener { view ->
            var intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
    }
}
