package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChannelActivity : AppCompatActivity() {

    private lateinit var convRecyclerView: RecyclerView
    lateinit var sqliteHelper: DataBaseHandler
    private var convAdapter: ConvAdapter? = null
    private lateinit var ct: ArrayList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        sqliteHelper = DataBaseHandler(this)
        getConv()
        convRecyclerView = findViewById(R.id.convRecycleView)
        convRecyclerView.layoutManager = LinearLayoutManager(this)
        convAdapter = ConvAdapter(this, sqliteHelper)
        convRecyclerView.adapter = convAdapter
        convAdapter?.addItems()
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d4d")))
      //  val intent = Intent(this, ChatActivity::class.java)
       // val name = intent.getStringExtra("name")
     //   val receiverId = intent.getIntExtra("id", 0)
      //  intent.putExtra("name", name)
      //  intent.putExtra("id", receiverId)

     //   convAdapter?.addItems(sqliteHelper.getContact(receiverId))
     //   startActivity(intent)
    }

    public override fun onResume() {
        super.onResume()
        getConv()
    }

    private fun getConv() {
        ct = sqliteHelper.getAllConv()
    }

}

