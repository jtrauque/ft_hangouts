package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var btnLanguage:ImageButton
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
       // sqliteHelper.deleteTable()

        initView()
        initRecycleView()

        btnAdd.setOnClickListener {
            val intent = Intent(this, Save::class.java)
            startActivity(intent)
        }
        btnSms.setOnClickListener {
            val intent = Intent(this, ChannelActivity::class.java)
            startActivity(intent)
        }
        btnSet.setOnClickListener {
            val intent = Intent(this, PopUpColor::class.java)
            startActivity(intent)
        }
        btnLanguage.setOnClickListener {
            showChangeLang()
        }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            ct = it
        }

        supportActionBar!!.hide()
        activityColor()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private fun showChangeLang() {
        val listItems = arrayOf(getString(R.string.french), getString(R.string.english))

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(getString(R.string.choose))
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->

            if (which == 0) {
                setLocate("fr", this)
                recreate()
            } else if (which == 1) {
                setLocate("en", this)
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocate(Lang: String, context: Context) {

        val conf: Configuration = context.resources.configuration

        val sysLocale = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            conf.locales.get(0)
        } else {
            conf.locale
        }
        if (Lang != "" && (sysLocale.language != Lang)) {
            val locale = Locale(Lang)
            Locale.setDefault(locale)

            conf.locale = locale
            resources.updateConfiguration(conf, resources.displayMetrics)
        }

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun activityColor() {
        val colorText = ColorManager.text
        title.setBackgroundColor(Color.parseColor(ColorManager.back))
        title.text = Html.fromHtml("<font color=$colorText>" + getString(R.string.all_contacts))

        tape.setBackgroundColor(Color.parseColor(ColorManager.back))
        btnSet.setColorFilter(Color.parseColor(colorText))
        btnSms.setColorFilter(Color.parseColor(colorText))
        btnLanguage.setColorFilter(Color.parseColor(colorText))
    }

    public override fun onStart() {
        super.onStart()

        if (BackgroundCheck.time != "null" && BackgroundCheck.backOn) {
            val time = BackgroundCheck.time
            Toast.makeText(this, getString(R.string.time) + " $time", Toast.LENGTH_SHORT).show()
            BackgroundCheck.backOn = false
            BackgroundCheck.time = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        BackgroundCheck.backOn = true
        //App in background
    }

    public override fun onResume() {
        super.onResume()

        getContacts()
        activityColor()
    }

    @SuppressLint("SimpleDateFormat")
    public override fun onStop() {
        super.onStop()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        BackgroundCheck.time = sdf.format(Date())
    }

    private fun getContacts() {
        //get all contacts
        val ctList = sqliteHelper.getAllContact()
        if (adapter == null)
            return

        adapter?.addItems(ctList){position -> deleteItem(position as Int)}
        adapter?.addItems(ctList){position -> modifyItem(position as Int)}
    }

    private fun deleteItem(position: Int) {
        if (::sqliteHelper.isInitialized) {
            sqliteHelper.getAllContact()
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
                position -> deleteItem(position)}, {
                position -> modifyItem(position)})
        recyclerView.adapter = adapter
    }

    private fun initView() {
        btnAdd = findViewById(R.id.btnAdd)
        btnSms = findViewById(R.id.btnConv)
        btnSet = findViewById(R.id.btnSet)
        btnLanguage = findViewById(R.id.btnLanguage)
        title = findViewById(R.id.btnView)
        tape = findViewById(R.id.tape)
        recyclerView = findViewById((R.id.recycleView))
    }
}

abstract class BackgroundCheck {
    companion object {
        var backOn: Boolean = false
        var time : String? = null
    }
}