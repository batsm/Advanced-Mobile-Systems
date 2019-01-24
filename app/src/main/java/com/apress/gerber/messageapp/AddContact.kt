package com.apress.gerber.messageapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContact : AppCompatActivity() {

    //private lateinit var database: DatabaseReference
    //database = FirebaseDatabase.getInstance().reference

    var fbAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference
    public var convoID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        database = FirebaseDatabase.getInstance().reference

        var userdatabase = database.child("user")
        var conversationdatabase = database.child("conversation")

        btnAddContact.setOnClickListener { view ->
            var foundUser: Boolean = false
            var email = txtContactEmail.text.toString()
            var otherUserID: String = ""
            val user = fbAuth?.currentUser

            userdatabase.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    showMessage(view, "Error: $p0!!")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                   if (dataSnapshot!!.exists()) {
                        for (i in dataSnapshot.children) {
                            val useremail = i.value.toString()
                            if (useremail == email){
                                foundUser = true
                            }
                        }
                       if (foundUser){
                           userdatabase.child(userEmail).child(convoID).setValue("ConversationID")//.setValue(fbAuth.currentUser!!.uid)
                           userdatabase.child(convoID).setValue("ConversationID")//setValue(fbAuth.currentUser!!.uid)
                           //conversationdatabase.child(fbAuth.currentUser!!.uid).setValue("New Conversation")
                           conversationdatabase.child(convoID).child("1").setValue("Conversation Started")
                           showMessage(view, "User Added!")
                       } else {
                           //showMessage(view, "Error: User not found")
                       }
                    }
                }
            })
        }
    }


    fun getNewConvoID(convoData: DatabaseReference) {
        //createConversation(conversationdatabase)
        convoData.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot!!.exists()) {
                    convoID = dataSnapshot.key + 1
                }
            }
        })
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}

