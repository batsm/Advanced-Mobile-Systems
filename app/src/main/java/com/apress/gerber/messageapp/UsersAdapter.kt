package com.apress.gerber.messageapp

import android.os.Message
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UsersAdapter(val messages: ArrayList<String>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text = messages[position]
    }

    override fun getItemCount() = messages.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.firstName)
    }

    fun addMessage(message: String){
        messages.add(message)
        notifyDataSetChanged()
    }
}