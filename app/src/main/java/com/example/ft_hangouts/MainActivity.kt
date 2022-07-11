package com.example.ft_hangouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
   // private lateinit var edName: EditText
   // private lateinit var edPhone: EditText
    lateinit var btnAdd:ImageButton
    lateinit var btnModify:Button
  //  lateinit var btnDelete:Button

    private lateinit var sqliteHelper: DataBaseHandler
    private lateinit var recyclerView: RecyclerView
    private var adapter: ContactAdapter? = null
    private var ct:Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteHelper = DataBaseHandler(this)

        initView()
        initRecycleView()
        //sqliteHelper = DataBaseHandler(this)
        btnAdd.setOnClickListener(){
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
        }
       // btnDelete.setOnClickListener{ }
      //  btnModify.setOnClickListener {
          //  ct = getContacts()
            //    sqliteHelper.updateContact(ct)
      //  }
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
        adapter?.addItems(ctList){position -> deleteItem(position as Int)}
    }

    private fun deleteItem(position: Int) {
        if (::sqliteHelper.isInitialized) {
            val ctList = sqliteHelper.getAllContact()
            ctList.removeAt(position)
            adapter?.setItems(ctList)
        }
    }
  //  private fun getContactRef() {

  //  }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(sqliteHelper.getAllContact()) {position -> deleteItem(position as Int)}
        recyclerView.adapter = adapter
    }

    private fun initView() {
        //edName = findViewById(R.id.edName)
        //edPhone = findViewById(R.id.edPhone)
     //   btnModify = findViewById<Button>(R.id.btnModify)
      //  btnDelete = findViewById<Button>(R.id.btnDelete)
        btnAdd = findViewById<ImageButton>(R.id.btnAdd)
     //   btnAdd.layoutParams = LinearLayout.LayoutParams(
       //     ViewGroup.LayoutParams.WRAP_CONTENT,
     //       ViewGroup.LayoutParams.WRAP_CONTENT)
      //  btnAdd.setImageResource(R.drawable.add)
        //btnView = findViewById(R.id.btnView)
        recyclerView = findViewById((R.id.recycleView))
    }
}