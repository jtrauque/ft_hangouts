package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
   // private lateinit var edName: EditText
   // private lateinit var edPhone: EditText
    lateinit var btnAdd:ImageButton
   // private lateinit var btnView:EditText

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
            Log.e("add contact", "1")
            val intent = Intent(this, Save::class.java)
            Log.e("add contact", "1")
            startActivity(intent)
        }
        //btnView.setOnClickListener{ getContacts()}
        getContacts()
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
           // sqliteHelper.updateContact(it)
            //Save.edName.setText(it.name)
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
       // btnAdd = ImageButton(this)
        btnAdd = findViewById<ImageButton>(R.id.btnAdd)
     //   btnAdd.layoutParams = LinearLayout.LayoutParams(
       //     ViewGroup.LayoutParams.WRAP_CONTENT,
     //       ViewGroup.LayoutParams.WRAP_CONTENT)
      //  btnAdd.setImageResource(R.drawable.add)
        //btnView = findViewById(R.id.btnView)
        recyclerView = findViewById((R.id.recycleView))
    }
}