package com.example.ft_hangouts

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConvAdapter(val context: Context, private val data: DataBaseHandler): RecyclerView.Adapter<ConvAdapter.ConvViewHolder>() {

    private var convList: ArrayList<Contact> = ArrayList()
    private var onClickItem: ((Contact) -> Unit)? = null

    inner class ConvViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.messName)
        private var btnDelete = view.findViewById<ImageButton>(R.id.btnDeleteConv)

        fun binView(ct: Contact, position: Int) {
            name.text = ct.name

            btnDelete.setColorFilter(Color.parseColor(ColorManager.back))
            btnDelete.setOnClickListener {
                deleteItem(position)
            }
        }
    }

    fun setOnClickItem(callback: (Contact)->Unit) {
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvAdapter.ConvViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.card_items_channels, parent, false)
        return ConvViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ConvAdapter.ConvViewHolder, position: Int) {
        val ct = convList[position]

        holder.binView(ct, position)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", ct.name)
            intent.putExtra("id", ct.id)
            intent.putExtra("phone", ct.phone)
            context.startActivity(intent) //to chat with the person
        }
    }

    override fun getItemCount(): Int {
        return convList.size
    }

    fun addItems() {
        convList = data.getAllConv()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        data.deleteConv(convList[position])
        this.convList.removeAt(position)
        notifyDataSetChanged()
    }
}