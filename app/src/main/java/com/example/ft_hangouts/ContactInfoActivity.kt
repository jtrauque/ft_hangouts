package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ContactInfo : AppCompatActivity() {
    private lateinit var btnModify: Button
    private lateinit var btnDelete: Button
    private lateinit var btnSms: ImageButton
    private lateinit var infoName: TextView
    private lateinit var infoPhone: TextView
    private lateinit var infoAddress: TextView
    private lateinit var infoMail: TextView
    private lateinit var infoBirth: TextView
    private lateinit var title : TextView

    private lateinit var name: String
    private lateinit var phone: String
    private var id = 0

    private lateinit var sqliteHelper: DataBaseHandler

    private var time:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)

        name = intent.getStringExtra("name").toString()
        id = intent.getIntExtra("id", 0)
        phone = intent.getStringExtra("phone").toString()

        initView()
        activityColor()

        sqliteHelper = DataBaseHandler(this)

        var ct : Contact = sqliteHelper.getContact(id)
        infoName.text = ct.name
        infoPhone.text = ct.phone
        infoAddress.text = ct.address
        infoMail.text = ct.mail
        infoBirth.text = ct.birth

        btnDelete.setOnClickListener {
            Log.e("DELETE", name)
            sqliteHelper.deleteContact(sqliteHelper.getContact(id))
            onBackPressed()
        }

        btnModify.setOnClickListener(){
            val intent = Intent(this, Modify::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        btnSms.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)

            intent.putExtra("name", name)
            intent.putExtra("id", id)
            intent.putExtra("phone", phone)

            startActivity(intent) //to chat with the person
        }
    }

    public override fun onResume() {
        super.onResume()
        if (time != "null")
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        time = sdf.format(Date())
    }

    private fun activityColor() {
        var colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>Details on $name")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>Contact Details")

        btnDelete.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnDelete.setTextColor(Color.parseColor(ColorManager.text))
        btnModify.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnModify.setTextColor(Color.parseColor(ColorManager.text))
        btnSms.setColorFilter(Color.parseColor(ColorManager.back))
    }

    private fun initView() {
        btnModify = findViewById(R.id.btnModify)
        btnDelete = findViewById(R.id.btnDelete)
        btnSms = findViewById(R.id.btnSms)
        infoName = findViewById(R.id.nameDetails)
        infoPhone = findViewById(R.id.phoneDetails)
        infoAddress = findViewById(R.id.addressDetails)
        infoMail = findViewById(R.id.mailDetails)
        infoBirth = findViewById(R.id.birthdayDetails)
        title = findViewById<TextView>(R.id.title)
    }
}