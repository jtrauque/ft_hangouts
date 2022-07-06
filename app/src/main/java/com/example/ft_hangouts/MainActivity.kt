package com.example.ft_hangouts

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private lateinit var btnAdd:Button
    private lateinit var btnView:Button

    private lateinit var sqliteHelper: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        sqliteHelper = DataBaseHandler(this) //

        btnAdd.setOnClickListener(){
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
            //addContact()
        }
        btnView.setOnClickListener{ getContacts()}
    }

    private fun getContacts() {
        //get all contacts
        val ctList = sqliteHelper.getAllContact()
        Log.e("pppp", "${ctList.size}")
    }

    private fun initView() {
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
    }
}