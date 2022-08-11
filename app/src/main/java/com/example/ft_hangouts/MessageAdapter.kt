package com.example.ft_hangouts

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context, private val data: DataBaseHandler): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemReceived = 1
    private val itemSent = 2
    private var onClickItem: ((Message) -> Unit)? = null
    private var messList: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) { //itemReceived
            //inflate receive
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.received, parent, false)
            ReceiveViewHolder(view)
        } else {
            //inflate sent
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.sent, parent, false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messList[position]

        if(holder.javaClass == SentViewHolder::class.java) { // if the current use is sent
            //do the stuff for sent
            val viewHolder = holder as SentViewHolder
            viewHolder.sentMessage.text = currentMessage.message

        } else {
            //do the stuff for receive
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receivedMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int { //
        val currentMessage = messList[position]

        return if (currentMessage.senderId == 0) {
            itemSent
        } else {
            itemReceived
        }
    }

    override fun getItemCount(): Int {
        return messList.size
    }

    fun add(receiverId: Int) {
        this.messList = data.getMessages(receiverId)  //ctList
        notifyDataSetChanged()
    }

    inner class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.sentMessage)
    }

    inner class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivedMessage : TextView = itemView.findViewById(R.id.receivedMessage)

    }
}