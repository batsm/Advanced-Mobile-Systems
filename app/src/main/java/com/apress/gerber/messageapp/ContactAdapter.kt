package com.apress.gerber.messageapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ContactAdapter (private val contactList: ArrayList<ContactData>): RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.mainTitle.text = contactList[p1].username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_format, parent, false))
    }

    override fun getItemCount() = contactList.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mainTitle = itemView.findViewById<Button>(R.id.btnContact)//itemView.findViewById<TextView>(R.id.text)!!

    }
}