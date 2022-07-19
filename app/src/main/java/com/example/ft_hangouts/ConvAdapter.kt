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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvAdapter.ConvViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.activity_channel, parent, false)
        return ConvViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ConvAdapter.ConvViewHolder, position: Int) {
        val ct = data.getAllConv()[position]
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
        return data.getAllContact().size
    }

    inner class ConvViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.messName)
        private var ct = data.getAllConv()

        fun binView(ct: Contact, position: Int) {
            val btnDelete = view.findViewById<Button>(R.id.btnDelete)
            Log.e("name = ", ct.name)
            name.text = ct.name

            btnDelete.setOnClickListener {
                Log.e("plop", position.toString())
                deleteItem(position)
            }
        }

        private fun deleteItem(position: Int) {
            data.deleteConv(ct[position])
            this.ct.removeAt(position)
            notifyDataSetChanged()
        }
    }
}