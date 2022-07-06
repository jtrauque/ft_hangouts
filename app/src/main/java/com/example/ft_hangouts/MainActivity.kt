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

   // private lateinit var edName:EditText
  //  private lateinit var edPhone:EditText
    private lateinit var btnAdd:Button
    private lateinit var btnView:Button

    private lateinit var sqliteHelper: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initView()
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
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
   /* private fun addContact() {
        val name = edName.text.toString()
        val phone = edPhone.text.toString()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter both required fields", Toast.LENGTH_SHORT).show()
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
        } else {
            val ct = Contact(name = name, phone = phone)
            val status = sqliteHelper.insertContact(ct)
            if (status < -1) {
                Toast.makeText(this, "Contact created...", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearEditText() {
        edName.setText("")
        edPhone.setText("")
        edName.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edPhone = findViewById(R.id.edPhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
    }

    */
}