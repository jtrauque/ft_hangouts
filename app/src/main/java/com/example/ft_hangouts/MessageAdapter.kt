package com.example.ft_hangouts

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val context: Context, private val data: DataBaseHandler): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECIEVE = 1
    val ITEM_SENT = 2
    private var onClickItem: ((Message) -> Unit)? = null
    private var messList: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) { //ITEM_RECIEVE
            //inflate receive
            Log.e("onCreate MA =", viewType.toString())
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.received, parent, false)
            return ReceiveViewHolder(view)
        } else {
            //inflate sent
            Log.e("onCreate MA =", viewType.toString())
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messList[position]

        Log.e("ON BIND=", position.toString() )

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

        Log.e("getItemView =", messList[position].senderId.toString())
        if (currentMessage.senderId == 0) {
            return ITEM_SENT
        } else {
            return ITEM_RECIEVE
        }
    }

    override fun getItemCount(): Int {
        return messList.size
    }

    fun add(receiverId: Int) {
        Log.e("add MessageAdapt =", receiverId.toString())
        this.messList = data.getMessages(receiverId) as ArrayList<Message> //ctList
      //  this.messList += data.getMessages(receiverid, senderId) as ArrayList<Message> //ctList
        Log.e("MessageAdapt size =", this.messList.size.toString())
        notifyDataSetChanged()
    }

    inner class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.sentMessage)
    }

    inner class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.receivedMessage)

    }
}