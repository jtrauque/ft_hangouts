package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChannelActivity : AppCompatActivity() {

    private lateinit var convRecyclerView: RecyclerView
    lateinit var sqliteHelper: DataBaseHandler
    private var adapter: ConvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        sqliteHelper = DataBaseHandler(this)

        convRecyclerView = findViewById((R.id.convRecycleView))
        convRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ConvAdapter(this, sqliteHelper)
        convRecyclerView.adapter = adapter

    }
}

