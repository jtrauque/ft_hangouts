package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class Save : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edPhone: EditText
    private lateinit var btnSave: Button
    lateinit var sqliteHelper: DataBaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        supportActionBar?.title = "Contact creation" //to have the receiver name on top of your screen
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d4d")))

        initView()
        sqliteHelper = DataBaseHandler(this)
        btnSave.setOnClickListener(){
            addContact()
        }
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edPhone = findViewById(R.id.edPhone)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun addContact() {
        val name = edName.text.toString()
        val phone = edPhone.text.toString()

        Log.e("in add contact function", "2")
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter both required fields", Toast.LENGTH_SHORT).show()
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
        } else {
            val ct = Contact(name = name, phone = phone)
            val status = sqliteHelper.insertContact(ct)
            if (status == (-1).toLong()) {
                Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Contact created...", Toast.LENGTH_SHORT).show()
                clearEditText()
            }
        }
    }

    private fun clearEditText() {
        edName.setText("")
        edPhone.setText("")
        edName.requestFocus()
    }
}