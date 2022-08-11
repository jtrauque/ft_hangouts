package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)

        name = intent.getStringExtra("name").toString()
        id = intent.getIntExtra("id", 0)
        phone = intent.getStringExtra("phone").toString()

        initView()
        activityColor()

        sqliteHelper = DataBaseHandler(this)

        val ct : Contact = sqliteHelper.getContact(id)
        infoName.text = ct.name
        infoPhone.text = ct.phone
        infoAddress.text = ct.address
        infoMail.text = ct.mail
        infoBirth.text = ct.birth

        btnDelete.setOnClickListener {
            sqliteHelper.deleteContact(sqliteHelper.getContact(id))
            onBackPressed()
        }

        btnModify.setOnClickListener {
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

    public override fun onStart() {
        super.onStart()
        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            val time = BackgroundCheck.time
            Toast.makeText(this, getString(R.string.time) + " $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
        }
    }

    override fun onResume() {
        super.onResume()

        val ct : Contact = sqliteHelper.getContact(id)
        infoName.text = ct.name
        infoPhone.text = ct.phone
        infoAddress.text = ct.address
        infoMail.text = ct.mail
        infoBirth.text = ct.birth
    }

    @SuppressLint("SimpleDateFormat")
    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
    }

    private fun activityColor() {
        val colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>" + getString(R.string.info) + " $name")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>" + getString(R.string.contact_details))

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
        title = findViewById(R.id.title)
    }
}