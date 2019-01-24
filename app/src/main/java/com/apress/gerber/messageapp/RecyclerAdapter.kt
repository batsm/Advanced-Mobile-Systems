package com.apress.gerber.messageapp
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*


class RecyclerAdapter(private var messages: String) : RecyclerView.Adapter<RecyclerAdapter.MessageHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MessageHolder {//(p0: ViewGroup, p1: Int): RecyclerAdapter.Photoholder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return MessageHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return  1
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MessageHolder, position: Int) {//(p0: RecyclerAdapter.Photoholder, p1: Int) {
        val itemMessage = messages
        holder.bindMessage(itemMessage)
    }

    class MessageHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var message: String = "Test"//messages? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }

        fun bindMessage(messages: String){
            this.message = message
            //Get message
        }
    }
}