package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_contacts_page.*
import kotlinx.android.synthetic.main.activity_messages.*

class ContactsPageActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference
    var re = Regex("[^a-zA-Z0-9 -]")
    var reDash = Regex("-")
    private var contacts = ArrayList<ContactData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_page)

        database = FirebaseDatabase.getInstance().reference
        val user = FirebaseAuth.getInstance().currentUser

        txtContactText.text = FirebaseAuth.getInstance().uid
        val userEmail = fbAuth?.currentUser
        var myUsername = re.replace(userEmail!!.email.toString(), "")
        var myReg = Regex("$myUsername")

        btnAddContactsPage.setOnClickListener { view ->
            var intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }

        btnMessagesPage.setOnClickListener { view ->
            var intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }

        database.child("chat").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children){
                 if (i.key.toString().contains(myUsername, false)){
                     var chatName = i.key.toString()
                     var contactName = myReg.replace(chatName, "")
                     contactName = reDash.replace(contactName, "")
                     contacts.add(ContactData(contactName))
                 }
                }
                contactRecyclerView.layoutManager = LinearLayoutManager(this@ContactsPageActivity, LinearLayout.VERTICAL, false)
                contactRecyclerView.adapter = ContactAdapter(contacts)
            }
        })
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
