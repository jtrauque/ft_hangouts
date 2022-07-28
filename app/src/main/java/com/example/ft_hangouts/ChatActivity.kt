package com.example.ft_hangouts

import android.Manifest
import android.annotation.SuppressLint
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
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
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
       // registerReceiver(smsListener, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

        sqliteHelper = DataBaseHandler(this)

        val senderId: Int = 0 // a lier avec le base de donnee

        val name = intent.getStringExtra("name")
        receiverId = intent.getIntExtra("id", 0)
        val phone = intent.getStringExtra("phone")
        //Log.e("PHONE CHAT", phone.toString())
        Log.e(TAG, "onCreate chat for this external number : $phone")

        getPermission(Manifest.permission.RECEIVE_SMS)

        supportActionBar?.title = name //to have the receiver name on top of your screen
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#004d4d")))

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
            messageAdapter.add(senderId, receiverId)
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
        Log.e("On START", "0")
        Log.e(TAG, "onStart")
      //  registerReceiver(smsListener, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("On DESTROY", "0")
        Log.e(TAG, "onDestroy: register receiver ")
        //unregisterReceiver(SMSReceiver())
    }
  //  private val PERMISSIONS_REQUEST = 1

    //https://androidexample365.com/sample-of-how-to-intercept-a-sms-with-broadcastreceiver/
    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(permission: String) {
        Log.e("GET PERMISSION", TAG)
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//            if (shouldShowRequestPermissionRationale(permission)) {
//                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show()
//            }
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }
       // afterPermission()
    }

//    private fun afterPermission() {
//        registerReceiver(SMSReceiver(), IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
//    }

//    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
//        if (requestCode == PERMISSIONS_REQUEST) {
//            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
//    }

//    private val smsListener : BroadcastReceiver = object : BroadcastReceiver()  {
//        override fun onReceive(context: Context?, intent: Intent) {
//            Log.e("ON RECEIVE:", "0")
//            Log.e(TAG, "onReceive with $intent")
//
//            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
//                for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
//                    val message = smsMessage.messageBody
//                    val num = smsMessage.originatingAddress
//                   // Log.e("LISTENER M:", message)
//                   // Log.e("LISTENER P:", num.toString())
//                    Log.e(TAG, "Recv new message: $message | From : $num")
//                    val messageObjectRev = Message(message, sqliteHelper.getID(num.toString()), 0)
//                    messageAdapter?.add(0, sqliteHelper.getID(num.toString()))
//                    sqliteHelper.newMessage(messageObjectRev)
//                }
//            }
//        }
//    }
}






