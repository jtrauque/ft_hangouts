package com.example.ft_hangouts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter (var context: Context, private val data: DataBaseHandler, val onClickDelete: (Int) -> Unit, val onClickModify: (Int) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    inner class ContactViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)

        fun binView(ct : Contact, position : Int) {
            val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)
            val btnSms = view.findViewById<ImageButton>(R.id.btnSms)
            name.text = ct.name
            phone.text = ct.phone

            btnDelete.setOnClickListener {
                deleteItem(position)
            }

            btnSms.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)

                intent.putExtra("name", ct.name)
                intent.putExtra("id", ct.id)
                intent.putExtra("phone", ct.phone)

                context.startActivity(intent) //to chat with the person
            }
        }
    }

    private var onClickItem: ((Contact) -> Unit)? = null
    private var ctList: ArrayList<Contact> = ArrayList()

    fun addItems(ctList: List<Contact>, param: (Any) -> Unit) {
        this.ctList = data.getAllContact() //ctList
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
            val intent = Intent(context, ContactInfo::class.java)

            intent.putExtra("name", ct.name)
            intent.putExtra("id", ct.id)
            intent.putExtra("phone", ct.phone)

            context.startActivity(intent) //to chat with the person

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
