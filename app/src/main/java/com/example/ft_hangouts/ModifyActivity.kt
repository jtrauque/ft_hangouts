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
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import java.text.SimpleDateFormat
import java.util.*

class Modify : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var sqliteHelper: DataBaseHandler

    private lateinit var ct:Contact

    private var usedID = 0

    private lateinit var newName : EditText
    private lateinit var newPhone : EditText
    private lateinit var newAddress : EditText
    private lateinit var newMail : EditText
    private lateinit var newBirth : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        initView()

        val intent: Intent = intent
        usedID = intent.getIntExtra("id", 0)

        sqliteHelper = DataBaseHandler(this)
        ct = sqliteHelper.getContact(usedID)

        supportActionBarTools()

        btnSave.setOnClickListener(){
            modifyContact()
        }
    }

    public override fun onStart() {
        super.onStart()

        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            var time = BackgroundCheck.time
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
        }
    }

    override fun onStop() {
        super.onStop()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
    }

    private fun supportActionBarTools() {
        var name = ct.name
        var phone = ct.phone
        var colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>$name - $phone")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))
        btnSave.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnSave.setTextColor(Color.parseColor(ColorManager.text))
    }

    private fun initView() {
        newName = findViewById(R.id.usedName)
        newPhone = findViewById(R.id.usedPhone)
        newAddress = findViewById(R.id.usedAddress)
        newMail = findViewById(R.id.usedMail)
        newBirth = findViewById(R.id.usedBirth)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun modifyContact() {

        var name = newName.text.toString()
        var phone = newPhone.text.toString()
        var address = newAddress.text.toString()
        var mail = newMail.text.toString()
        var birth = newBirth.text.toString()

        Log.e("MODIFY FUNCTION", "2")

        if (name.isEmpty()) {
            name = ct.name
        }
        if (phone.isEmpty()) {
            phone = ct.phone
        } else if (!phone.isDigitsOnly()) {
            Toast.makeText(this, "Please enter numbers only for the phone", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (address.isEmpty()) {
            address = ct.address
        }
        if (mail.isEmpty()) {
            mail = ct.mail
        }
        if (birth.isEmpty()) {
            birth = ct.birth
        }
        val ct = Contact(usedID, name = name, phone = phone, address = address, mail = mail, birth = birth)
        Log.e("modify contact function", ct.id.toString())
        val status = sqliteHelper.updateContact(ct)

        Log.e("contact function status", status.toString())
        if (status > 0) {
            Toast.makeText(this, "Contact modified...", Toast.LENGTH_SHORT).show()
        //  clearEditText()
        } else {
            Toast.makeText(this, "Record not saved...", Toast.LENGTH_SHORT).show()
        }
    }
}