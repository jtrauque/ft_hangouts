package com.example.ft_hangouts

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView

import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat.startActivity


class ContactAdapter (var context: Context, private val data: DataBaseHandler, val onClickDelete: (Int) -> Unit, val onClickModify: (Int) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){
    inner class ContactViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        private var ct = data.getAllContact()

        fun binView(ct : Contact, position : Int) {
            val btnDelete = view.findViewById<Button>(R.id.btnDelete)
            val btnModify = view.findViewById<Button>(R.id.btnModify)
            name.text = ct.name
            phone.text = ct.phone

            btnDelete.setOnClickListener {
                Log.e("plop", position.toString())
                deleteItem(position)
            }

            btnModify.setOnClickListener {
                val intent = Intent(context, Modify::class.java)
                intent.putExtra("name", ct.name)
                intent.putExtra("phone", ct.phone)
                intent.putExtra("id", ct.id)
                context.startActivity(intent)
                //this.ct = data.getAllContact()
                //notifyItemChanged(position)
            }
        }
    }

    private var onClickItem: ((Contact) -> Unit)? = null
    private var ctList: ArrayList<Contact> = ArrayList()

    fun addItems(ctList: List<Contact>, param: (Any) -> Unit) {
        this.ctList = data.getAllContact() as ArrayList<Contact> //ctList
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", ct.name)
            intent.putExtra("id", ct.id)

            context.startActivity(intent) //to chat with the person
            // onClickItem?.invoke(ct) } to get the name pop up
        }
    }

    override fun getItemCount(): Int {
        return ctList.size
    }

    fun setItems(ctList: List<Contact> ) {
        this.ctList = ctList as ArrayList<Contact>
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        data.deleteContact(ctList[position])
        this.ctList.removeAt(position)
        notifyDataSetChanged()
    }
}
