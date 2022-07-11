package com.example.ft_hangouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class Modify : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var sqliteHelper: DataBaseHandler
    var usedName = intent.getStringExtra("name")
    var usedPhone = intent.getStringExtra("phone")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        initView()
        sqliteHelper = DataBaseHandler(this)
        btnSave.setOnClickListener(){
            addContact()
        }
    }

    private fun initView() {
       // usedName = getIntent.putExtras("phone")
       // usedPhone = intent.extras?.getString("phone")


        btnSave = findViewById(R.id.btnSave)
       // usedName = findViewById(R.id.usedName)
       // usedPhone = findViewById(R.id.usedPhone)
       // btnSave = findViewById(R.id.btnSave)
    }

    private fun addContact() {
        val name = usedName
        val phone = usedPhone

        Log.e("in add contact function", "2")
        /*
        if (name.isEmpty() || phone?.isEmpty()) {
            Toast.makeText(this, "Please enter both required fields", Toast.LENGTH_SHORT).show()
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
        } else {*/
            val ct = name?.let {
                if (phone != null) {
                    Contact(name = name, phone = phone)
                }
            }
            val status = sqliteHelper.updateContact(ct)
            if (status < -1) {
                Toast.makeText(this, "Contact created...", Toast.LENGTH_SHORT).show()
              //  clearEditText()
            } else {
                Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
            }
    }


/*
    private fun clearEditText() {
        usedName.setText("")
        usedPhone.setText("")
        usedName.requestFocus()
    }*/
}