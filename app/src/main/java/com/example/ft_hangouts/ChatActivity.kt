package com.example.ft_hangouts

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.text.Html
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var sqliteHelper: DataBaseHandler

    private var receiverId: Int = 0
    private lateinit var message: String


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
       // registerReceiver(broadCastReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        sqliteHelper = DataBaseHandler(this)

        val senderId: Int = 0 // a lier avec le base de donnee
        val name = intent.getStringExtra("name").toString()
        receiverId = intent.getIntExtra("id", 0)
        val phone = intent.getStringExtra("phone").toString()
        Log.e(TAG, "onCreate chat for this external number : $phone")

        getPermission(Manifest.permission.RECEIVE_SMS)

        var colorText: String = ColorManager.text
        supportActionBar?.title = Html.fromHtml("<font color=$colorText>$name +1$phone")
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(ColorManager.back)))

        messageRecyclerView = findViewById(R.id.messageRecycleView)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendBtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, sqliteHelper)
        messageRecyclerView.adapter = messageAdapter
        messageAdapter?.add(receiverId)

        //add data to recycleView

        sendButton.setOnClickListener {
            message = messageBox.text.toString()
            val messageObject = Message(message, senderId, receiverId)

            sqliteHelper.newMessage(messageObject)
            messageAdapter.add(receiverId)
            messageBox.setText("")

            try {
                val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.getSystemService(SmsManager::class.java)
                } else {
                    SmsManager.getDefault()
                }
                smsManager.sendTextMessage("+1$phone", null, message, null, null)
                Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "Please enter all the data.." + e.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadCastReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        messageAdapter.add(receiverId)
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("On DESTROY", "0")
        Log.e(TAG, "onDestroy: register receiver ")
        unregisterReceiver(broadCastReceiver)
    }

    //https://androidexample365.com/sample-of-how-to-intercept-a-sms-with-broadcastreceiver/
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(permission: String) {
        Log.e("GET PERMISSION", TAG)
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }
    }

    private val broadCastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            Log.e("BROADCAST VAR", "0")
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
                    messageAdapter.add(receiverId)
            }
        }
    }
}







