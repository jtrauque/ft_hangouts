package com.example.ft_hangouts

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var onClickItem: ((Contact) -> Unit)? = null
    private var ctList: ArrayList<Contact> = ArrayList()

    fun addItems(items: ArrayList<Contact>) {
        this.ctList = items
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
        holder.binView(ct)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(ct) }
    }

    override fun getItemCount(): Int {
        return ctList.size
    }

    class ContactViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        private var btnDelete = view.findViewById<TextView>(R.id.btnDelete)
        private var btnModify = view.findViewById<TextView>(R.id.btnModify)

        fun binView(ct : Contact) {
            id.text = ct.id.toString()
            name.text = ct.name
            phone.text = ct.phone

        }
    }
}