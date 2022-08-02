package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class Modify : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var sqliteHelper: DataBaseHandler
    private lateinit var usedName : String
    private lateinit var usedPhone : String
    private var usedID = 0
    private lateinit var newName : EditText
    private lateinit var newPhone : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        initView()
        val intent: Intent = intent
        usedName = intent.getStringExtra("name").toString()
        usedPhone = intent.getStringExtra("phone").toString()
        usedID = intent.getIntExtra("id", 0)

        supportActionBar?.title = "$usedName - $usedPhone" //to have the receiver name on top of your screen
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d4d")))

        sqliteHelper = DataBaseHandler(this)
        btnSave.setOnClickListener(){
            modifyContact()
        }
    }

    private fun initView() {
        newName = findViewById(R.id.usedName)
        newPhone = findViewById(R.id.usedPhone)
        btnSave = findViewById(R.id.btnSaveMod)
    }

    private fun modifyContact() {
        val name = newName.text.toString()
        val phone = newPhone.text.toString()

        Log.e("in add contact function", "2")

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "No changes", Toast.LENGTH_SHORT).show()
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
        } else {
            val ct = Contact(usedID, name = name, phone = phone)
            Log.e("modify contact function", ct.id.toString())
            val status = sqliteHelper.updateContact(ct)

            Log.e("contact function status", status.toString())
            if (status > 0) {
                Toast.makeText(this, "Contact created...", Toast.LENGTH_SHORT).show()
                //  clearEditText()
            } else {
                Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
            }
        }

    }


/*
    private fun clearEditText() {
        usedName.setText("")
        usedPhone.setText("")
        usedName.requestFocus()
    }*/
}