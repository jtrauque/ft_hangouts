package com.example.ft_hangouts

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), LifecycleObserver {
    private lateinit var btnAdd:ImageButton
    private lateinit var btnSms:ImageButton
    private lateinit var btnSet:ImageButton
    private lateinit var title : TextView
    private lateinit var tape : LinearLayout

    private lateinit var sqliteHelper: DataBaseHandler
    private lateinit var recyclerView: RecyclerView
    private var adapter: ContactAdapter? = null
    private var ct:Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteHelper = DataBaseHandler(this)
        //sqliteHelper.deleteTable()

        initView()
        initRecycleView()

        btnAdd.setOnClickListener(){
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
        }
        btnSms.setOnClickListener(){
            val intent = Intent(this, ChannelActivity::class.java)
            startActivity(intent)
        }
        btnSet.setOnClickListener(){
            val intent = Intent(this, PopUpColor::class.java)
            startActivity(intent)
        }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            ct = it
        }

        supportActionBar!!.hide()
        activityColor()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private fun activityColor() {
        var colorText = ColorManager.text
        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>All Contacts")

        tape.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnSet.setColorFilter(Color.parseColor(colorText))
        btnSms.setColorFilter(Color.parseColor(colorText))
    }

    public override fun onStart() {
        super.onStart()
        Log.e("MAIN", "On START")
        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            var time = BackgroundCheck.time
            Toast.makeText(this, "Last used : $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
            BackgroundCheck.time = "null"
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Log.e("MAIN", "BACKGROOOOOUND")
        BackgroundCheck.backOn = true
        //App in background
    }

    public override fun onResume() {
        super.onResume()
        Log.e("MAIN", "On RESUME")
        getContacts()
        activityColor()
    }

    override fun onPause() {
        super.onPause()
        Log.e("MAIN", "On PAUSE")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MAIN", "On DESTROY")
    }

    public override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
        Log.e("MAIN", "On STOP")
    }

    private fun getContacts() {
        //get all contacts
        val ctList = sqliteHelper.getAllContact()
        if (adapter == null)
            return

        Log.e("pppp", "${ctList.size}")
        adapter?.addItems(ctList){position -> deleteItem(position as Int)}
        adapter?.addItems(ctList){position -> modifyItem(position as Int)}
    }

    private fun deleteItem(position: Int) {
        Log.e("ppppdeleteI", "$position")
        if (::sqliteHelper.isInitialized) {
            val ctList = sqliteHelper.getAllContact()
        }
    }

    private fun modifyItem(position: Int) {
        if (::sqliteHelper.isInitialized) {
            val ctList = sqliteHelper.getAllContact()
            sqliteHelper.updateContact(ctList[position])
            adapter?.setItems(ctList)
        }
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(this, sqliteHelper, {
                position -> deleteItem(position as Int)}, {
                position -> modifyItem(position as Int)})
        recyclerView.adapter = adapter
    }

    private fun initView() {
        btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        btnSms = findViewById<ImageButton>(R.id.btnConv)
        btnSet = findViewById<ImageButton>(R.id.btnSet)
        title = findViewById<TextView>(R.id.btnView)
        tape = findViewById<LinearLayout>(R.id.tape)
        recyclerView = findViewById((R.id.recycleView))
    }
}

abstract class BackgroundCheck(){
    companion object {
        var backOn: Boolean = false
        var time : String = "null"
    }
}