package com.example.ft_hangouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context, private val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECIEVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) { //ITEM_RECIEVE
            //inflate receive
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.received, parent, false)
            return ReceiveViewHolder(view)
        } else {
            //inflate sent
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java) { // if the current use is sent
            //do the stuff for sent
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message

        } else {
            //do the stuff for receive
            val viewHolder = holder as ReceiveViewHolder
            holder.receivedMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int { //
        val currentMessage = messageList[position]

        //if(userid == sender)
        //return ITEM_SENT
        //else return ITEM_RECIEVE
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMessage)
    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.receivedMessage)
    }
}