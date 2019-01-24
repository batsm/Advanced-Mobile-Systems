package com.apress.gerber.messageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logged_in.*
import org.w3c.dom.Text

class LoggedInActivity : AppCompatActivity() {

    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)


        //var btnLogin = btnCreateAccount //findViewById<Button>(R.id.btnCreateAccount)
        //var email = txtEmail // findViewById<TextView>(R.id.txtEmail)
        //var password = txtPassword //findViewById<TextView>(R.id.txtPassword)

        btnCreateAccount.setOnClickListener { view ->
            createAccout(view, txtEmail.text.toString(), txtPassword.text.toString())
        }
    }

    fun createAccout(view: View, email: String, password: String) {
        showMessage(view, "Creating...")

        fbAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showMessage(view, "Account created!")
                } else {
                    showMessage(view, "Error: ${task.exception?.message}")
                }
            }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
