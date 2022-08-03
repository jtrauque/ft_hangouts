package com.example.ft_hangouts

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd:ImageButton
    private lateinit var btnSms:ImageButton
    private lateinit var btnSet:ImageButton

    private lateinit var sqliteHelper: DataBaseHandler
    private lateinit var recyclerView: RecyclerView
    private var adapter: ContactAdapter? = null
    private var ct:Contact? = null
    private var time:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteHelper = DataBaseHandler(this)
        //sqliteHelper.deleteTable()

        initView()
        initRecycleView()

        btnAdd.setOnClickListener(){
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
        }
        btnSms.setOnClickListener(){
            val intent = Intent(this, ChannelActivity::class.java)
            startActivity(intent)
        }
        btnSet.setOnClickListener(){
            val intent = Intent(this, PopUpColor::class.java)
            startActivity(intent)
        }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            ct = it
        }
        supportActionBar!!.hide()

    }

   public override fun onResume() {
        super.onResume()
        getContacts()
        if (time != "null")
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
    }

    public override fun onPause() {
        super.onPause()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        time = sdf.format(Date())
    }
    private fun getContacts() {
        //get all contacts
        val ctList = sqliteHelper.getAllContact()
        if (adapter == null)
            return

        Log.e("pppp", "${ctList.size}")
        adapter?.addItems(ctList){position -> deleteItem(position as Int)}
        adapter?.addItems(ctList){position -> modifyItem(position as Int)}
    }

    private fun deleteItem(position: Int) {
        Log.e("ppppdeleteI", "$position")
        if (::sqliteHelper.isInitialized) {
            val ctList = sqliteHelper.getAllContact()
        }
    }

    private fun modifyItem(position: Int) {
        if (::sqliteHelper.isInitialized) {
            val ctList = sqliteHelper.getAllContact()
            sqliteHelper.updateContact(ctList[position])
            adapter?.setItems(ctList)
        }
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(this, sqliteHelper, {
                position -> deleteItem(position as Int)}, {
                position -> modifyItem(position as Int)})
        recyclerView.adapter = adapter
    }

    private fun initView() {
        btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        btnSms = findViewById<ImageButton>(R.id.btnSms)
        btnSet = findViewById<ImageButton>(R.id.btnSet)
        recyclerView = findViewById((R.id.recycleView))
    }
}