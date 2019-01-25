package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_account.*
import com.google.firebase.database.*

class CreateAccountActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    private lateinit var database: DatabaseReference
    var re = Regex("[^a-zA-Z0-9 -]")
    var usernameTaken = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //var database2 = FirebaseDatabase.getInstance().reference.child("users")

        btnCreateAccount.setOnClickListener{view ->
            if (txtPassword1.text.toString() == txtPassword2.text.toString())
            {
                createAccount(view, txtEmail.text.toString(), txtPassword1.text.toString())
            }
            else
            {
                showMessage(view, "Error: Passwords dont match")
            }
        }

        btnLogin.setOnClickListener { view ->
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun createAccount(view: View, email: String, password: String) {
        showMessage(view, "Creating Account...")
        database = FirebaseDatabase.getInstance().reference
        usernameTaken = false

        database.child("users").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children){
                    Log.d("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII Snapshot", i.key.toString())
                    if (txtUsername.text.toString() == i.key.toString())
                    {
                        showMessage(view, "Error: Username already taken")
                        usernameTaken = true
                        Log.d("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII MATCH", i.key.toString())
                    }
                }
            }
        })

        if (!usernameTaken) {
            fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //go to next activity here if success

                        //database = FirebaseDatabase.getInstance().reference

                        var UsernameEmail = re.replace(email, "")
                        database.child("users").child(txtUsername.text.toString())
                            .setValue(UsernameEmail)//.child(UsernameEmail)//.push().setValue(email)
                        showMessage(view, "Account created!")

                        /*
                    var database = FirebaseDatabase.getInstance().getReference("user")
                    database.child(fbAuth.currentUser!!.uid).setValue(email)//child("user").child(email).setValue(fbAuth.currentUser!!.uid)
                    */

                        //database.child("user").child(fbAuth.currentUser!!.uid).setValue()
                        //var user_id = fbAuth.currentUser!!.uid
                        //val key = database.child("user").push().key
                        //it.uuid = key
                        //var database curent_db = database2.child(user_id)

                        var intent = Intent(this, ContactsPageActivity::class.java)
                        startActivity(intent)
                    } else {
                        showMessage(view, "Error: ${task.exception?.message}")
                    }
                }
        }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }

    @IgnoreExtraProperties
    data class User(
        var username: String? = "",
        var email: String? = ""
    )
}
