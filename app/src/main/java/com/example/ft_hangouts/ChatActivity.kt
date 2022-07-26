package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    //private lateinit var convAdapter: ConvAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var sqliteHelper: DataBaseHandler
    private var receiverId: Int = 0
    var receiverRoom: Int = 0
    var senderRoom: Int = 0
   // private lateinit var listener: BroadcastReceiver
    private lateinit var message: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sqliteHelper = DataBaseHandler(this)
     //   listener = smsListener

        val senderId: Int = 0 // a lier avec le base de donnee

        val name = intent.getStringExtra("name")
        receiverId = intent.getIntExtra("id", 0)
        val phone = intent.getStringExtra("phone")

        // senderRoom = receiverId + senderId
        // receiverRoom = senderId + receiverId
        supportActionBar?.title = name //to have the receiver name on top of your screen
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d4d")))
        // convAdapter = ConvAdapter(this, sqliteHelper)
        //  convAdapter.addItems(sqliteHelper.getContact(receiverId))
        messageRecyclerView = findViewById(R.id.messageRecycleView)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendBtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, sqliteHelper)
        messageRecyclerView.adapter = messageAdapter
        messageAdapter?.add(receiverId, 0)
        //add data to recycleView

        sendButton.setOnClickListener {
            message = messageBox.text.toString()
            val messageObject = Message(message, senderId, receiverId)
       //     val messageObjectReverse = Message(message, receiverId, senderId)

            sqliteHelper.newMessage(messageObject)
            messageAdapter.add(receiverId, senderId)
          //  sqliteHelper.newMessage(messageObjectReverse)
         //   messageAdapter.add(senderId, receiverId)
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
        val smsListener : BroadcastReceiver = object : BroadcastReceiver()  {
            override fun onReceive(context: Context?, intent: Intent) {

                if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
                    for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                        message = smsMessage.messageBody
                        Log.e("Received message:", message)
                        val num = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        val messageObjectRev = Message(message, 0, sqliteHelper.getID(num.toString()))
                        messageAdapter?.add(0, sqliteHelper.getID(num.toString()))
                        sqliteHelper.newMessage(messageObjectRev)
                    }
                }
            }
        }
//        super.onStart()
//        SmsListener()
        //  registerReceiver(SmsListener(),
        //      IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        //  )
    }
/*
    private val smsListener : BroadcastReceiver = object : BroadcastReceiver()  {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent) {

            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
                for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    val message = smsMessage.messageBody
                    val num = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val messageObjectRev = Message(message, 0, sqliteHelper.getID(num.toString()))
                    messageAdapter?.add(0, sqliteHelper.getID(num.toString()))
                    sqliteHelper.newMessage(messageObjectRev)
                }
            }
        }
    }*/
}


   // public override fun onResume() {
   //     super.onResume()
   //     sqliteHelper.getMessages(receiverId, 0)
  //  }




