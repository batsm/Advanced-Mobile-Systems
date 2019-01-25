package com.apress.gerber.messageapp

import android.os.Message
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UsersAdapter(val messages: ArrayList<String>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    fun addMessage(message: String) {
        messages.add(message)
        notifyItemInserted(messages.size)
    }

    fun clearMessages() {
        messages.clear()
        notifyDataSetChanged()
    }

    fun adapterSize(): Int{
        return messages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtMessage.text = messages[position]
    }

    override fun getItemCount() = messages.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMessage: TextView = itemView.findViewById(R.id.txtMessage)
    }

}