package com.example.ft_hangouts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
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
    private lateinit var sqliteHelper: DataBaseHandler
    private var convAdapter: ConvAdapter? = null
    private lateinit var ct: ArrayList<Contact>
    private lateinit var title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        sqliteHelper = DataBaseHandler(this)
        getConv()
        initView()
        convAdapter?.addItems()

        layoutManagement()
    }

    private fun layoutManagement() {
        val colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>" + resources.getString(R.string.home))
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>" + getString(R.string.chat))
    }

    private fun initView() {
        convRecyclerView = findViewById(R.id.convRecycleView)
        convRecyclerView.layoutManager = LinearLayoutManager(this)
        convAdapter = ConvAdapter(this, sqliteHelper)
        convRecyclerView.adapter = convAdapter

        title = findViewById(R.id.btnView)
    }

    public override fun onResume() {
        super.onResume()
        getConv()
    }

    override fun onStart() {
        super.onStart()
        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            val time = BackgroundCheck.time
            Toast.makeText(this, getString(R.string.time) + " $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
        }
    }

    override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
    }

    private fun getConv() {
        ct = sqliteHelper.getAllConv()
    }
}

