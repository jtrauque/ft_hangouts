package com.example.ft_hangouts

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter (private val data: List<Contact>, val onClickDelete: (Int) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){
    inner class ContactViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)

        fun binView(ct : Contact, position : Int) {
            val btnDelete = view.findViewById<Button>(R.id.btnDelete)
            name.text = ct.name
            phone.text = ct.phone

            btnDelete.setOnClickListener { deleteItem(position)}
        }
    }

    private var onClickItem: ((Contact) -> Unit)? = null
    private var ctList: ArrayList<Contact> = ArrayList()

    fun addItems(ctList: List<Contact>, param: (Any) -> Unit) {
        this.ctList = ctList as ArrayList<Contact>
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (Contact)->Unit) {
        this.onClickItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_contacts, parent, false)
    )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val ct = ctList[position]
        holder.binView(ct, position)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(ct) }

    }

    override fun getItemCount(): Int {
        return ctList.size
    }

    fun setItems(ctList: List<Contact> ) {
        this.ctList = ctList as ArrayList<Contact>
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        this.ctList.removeAt(position)
        notifyDataSetChanged()
    }
}