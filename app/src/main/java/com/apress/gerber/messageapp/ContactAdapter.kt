package com.apress.gerber.messageapp

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ContactAdapter (private val contactList: ArrayList<ContactData>): RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    fun clearContacts() {
        contactList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.mainTitle.text = contactList[p1].username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_format, parent, false))
    }

    override fun getItemCount() = contactList.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mainTitle = itemView.findViewById<TextView>(R.id.txtContact)//itemView.findViewById<TextView>(R.id.text)!!

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(itemView.context, MessagesActivity::class.java)
            otherUserEmail = mainTitle.text.toString()
            itemView.context.startActivity(intent)
        }
    }
}