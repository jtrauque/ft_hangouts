package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import java.text.SimpleDateFormat
import java.util.*

class Save : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edPhone: EditText
    private lateinit var edAddress: EditText
    private lateinit var edMail: EditText
    private lateinit var edBirth: EditText
    private lateinit var btnSave: Button

    lateinit var sqliteHelper: DataBaseHandler

    private var time:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        var colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>Contact creation")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        initView()

        btnSave.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnSave.setTextColor(Color.parseColor(ColorManager.text))

        sqliteHelper = DataBaseHandler(this)
        btnSave.setOnClickListener(){
            addContact()
        }
    }

    public override fun onResume() {
        super.onResume()
        if (time != "null")
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        time = sdf.format(Date())
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edPhone = findViewById(R.id.edPhone)
        edAddress = findViewById(R.id.edAddress)
        edMail = findViewById(R.id.edMail)
        edBirth = findViewById(R.id.edBirth)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun addContact() {
        val name = edName.text.toString()
        val phone = edPhone.text.toString()
        val address = edAddress.text.toString()
        val mail = edMail.text.toString()
        val birth = edBirth.text.toString()

        Log.e("in add contact function", "2")
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter both required fields", Toast.LENGTH_SHORT).show()
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
        } else {
            val ct = Contact(name = name, phone = phone, address = address, mail = mail, birth = birth)
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
        edAddress.setText("")
        edMail.setText("")
        edBirth.setText("")
        edName.requestFocus()
    }
}