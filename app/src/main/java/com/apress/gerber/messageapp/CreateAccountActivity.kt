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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

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

            fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var UsernameEmail = re.replace(email, "")
                        database.child("users").child(UsernameEmail).push().setValue(email)
                        showMessage(view, "Account created!")
                        var intent = Intent(this, ContactsPageActivity::class.java)
                        startActivity(intent)
                    } else {
                        showMessage(view, "Error: ${task.exception?.message}")
                    }
        }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
