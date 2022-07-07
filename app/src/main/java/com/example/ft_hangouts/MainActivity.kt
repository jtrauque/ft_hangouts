package com.example.ft_hangouts

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
   // private lateinit var edName: EditText
   // private lateinit var edPhone: EditText
    private lateinit var btnAdd:ImageButton
    private lateinit var btnView:Button

    private lateinit var sqliteHelper: DataBaseHandler
    private lateinit var recyclerView: RecyclerView
    private var adapter: ContactAdapter? = null
    private var ct:Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecycleView()
        sqliteHelper = DataBaseHandler(this) //

        btnAdd.setOnClickListener(){
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
        }
        btnView.setOnClickListener{ getContacts()}
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
        //    edName.setText(it.name)
        //    edPhone.setText(it.phone)
            ct = it
        }
    }

    private fun getContacts() {
        //get all contacts
        val ctList = sqliteHelper.getAllContact()
        Log.e("pppp", "${ctList.size}")
        adapter?.addItems(ctList)
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        //edName = findViewById(R.id.edName)
        //edPhone = findViewById(R.id.edPhone)
        btnAdd = ImageButton(this)
        btnAdd.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        btnAdd.setImageResource(com.google.android.material.R.drawable.material_ic_clear_black_24dp)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById((R.id.recycleView))
    }
}