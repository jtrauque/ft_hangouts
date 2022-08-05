package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChannelActivity : AppCompatActivity() {

    private lateinit var convRecyclerView: RecyclerView
    lateinit var sqliteHelper: DataBaseHandler
    private var convAdapter: ConvAdapter? = null
    private lateinit var ct: ArrayList<Contact>
    private var time:String = "null"
    private lateinit var title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        sqliteHelper = DataBaseHandler(this)
        getConv()
        initView()
        convAdapter?.addItems()

        var colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>Home")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>Chat")
    }

    private fun initView() {
        convRecyclerView = findViewById(R.id.convRecycleView)
        convRecyclerView.layoutManager = LinearLayoutManager(this)
        convAdapter = ConvAdapter(this, sqliteHelper)
        convRecyclerView.adapter = convAdapter

        title = findViewById<TextView>(R.id.btnView)
    }

    public override fun onResume() {
        super.onResume()
        getConv()
        if (time != "null")
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        time = sdf.format(Date())
    }

    private fun getConv() {
        ct = sqliteHelper.getAllConv()
    }

}

