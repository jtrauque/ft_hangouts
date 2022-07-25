package com.example.ft_hangouts

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConvAdapter(val context: Context, private val data: DataBaseHandler): RecyclerView.Adapter<ConvAdapter.ConvViewHolder>() {

    private var convList: ArrayList<Contact> = ArrayList()
    private var onClickItem: ((Contact) -> Unit)? = null

    inner class ConvViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.messName)
        private var ct = data.getAllConv()

        fun binView(ct: Contact, position: Int) {
            val btnDelete = view.findViewById<Button>(R.id.btnDeleteConv)
            Log.e("name = ", ct.name)
            name.text = ct.name

            btnDelete.setOnClickListener {
                Log.e("plop", position.toString())
              //  var plop = convList[position]
                deleteItem(position)
            }
            //addItems(this.ct[position])

        }
    }
    fun setOnClickItem(callback: (Contact)->Unit) {
        this.onClickItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvAdapter.ConvViewHolder {
        Log.e("Conv", "1")
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.card_items_channels, parent, false)
        return ConvViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ConvAdapter.ConvViewHolder, position: Int) {
        val ct = convList[position]
        holder.binView(ct, position)
        Log.e("plop ON BIND", position.toString())
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", ct.name)
            intent.putExtra("id", ct.id)

            context.startActivity(intent) //to chat with the person
            // onClickItem?.invoke(ct) } to get the name pop up
        }
    }

    override fun getItemCount(): Int {
        return convList.size
    }

    fun addItems() {
        convList = data.getAllConv()
        Log.e("ADD CONV ON BIND", convList.toString())
      //  this.convList = data.getAllConv() as ArrayList<Contact> //ctList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        data.deleteConv(convList[position])
        this.convList.removeAt(position)
        notifyDataSetChanged()
    }
}