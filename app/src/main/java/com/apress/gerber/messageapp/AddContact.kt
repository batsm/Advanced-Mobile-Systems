package com.apress.gerber.messageapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.util.Log
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
    //public var convoID: String = ""
    private var combinedUsername = "null"

    var re = Regex("[^a-zA-Z0-9 -]")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        database = FirebaseDatabase.getInstance().reference

        var userdatabase = FirebaseDatabase.getInstance().getReference("user").push()//database.child("user")
        var conversationdatabase = FirebaseDatabase.getInstance().getReference("conversations").push()//database.child("conversation")

        btnAddContact.setOnClickListener { view ->
            var foundUser = false
            var email = txtContactEmail.text.toString()
            val user = fbAuth?.currentUser

            database.child("users").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    showMessage(view, "Error: $p0!!")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                   if (dataSnapshot!!.exists()) {
                       var myUsername = re.replace(user!!.email.toString(), "")
                       email = re.replace(email, "")

                       Log.d("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII", myUsername)
                       Log.d("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII", email)

                       for (i in dataSnapshot.children) {
                            val useremail = i.key.toString()
                           Log.d("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII LOOP", useremail)
                           if (useremail == email){
                                foundUser = true
                            }
                        }
                       if (foundUser){
                           var theirUsername = re.replace(email, "")

                           combinedUsername = if(myUsername < theirUsername){
                               "$myUsername-$theirUsername"
                           } else {
                               "$theirUsername-$myUsername"
                           }
                           database.child("chat").child(combinedUsername).push().setValue("Start of conversation")

                           database.child("users").child(myUsername).child(combinedUsername).setValue("Chat")
                           database.child("users").child(theirUsername).child(combinedUsername).setValue("Chat")
                            showMessage(view, "Found user")
                           /*
                           var tests: String = conversationdatabase.toString()
                           userdatabase.child(userEmail).child(tests).setValue("ConversationID")//.setValue(fbAuth.currentUser!!.uid)
                           userdatabase.child(user!!.email.toString()).child(tests).setValue("ConversationID")//setValue(fbAuth.currentUser!!.uid)
                           //conversationdatabase.child(fbAuth.currentUser!!.uid).setValue("New Conversation")
                           conversationdatabase.child("Message").setValue("Conversation Started")
                           showMessage(view, "User Added!")*/
                       } else {
                           showMessage(view, "Error: User not found")
                       }
                    }
                }
            })
        }
    }

/*
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
    }*/

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}

