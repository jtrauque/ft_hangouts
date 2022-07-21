package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var sqliteHelper: DataBaseHandler

    var receiverRoom: Int = 0
    var senderRoom: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        sqliteHelper = DataBaseHandler(this)
        val senderId: Int = 0 // a lier avec le base de donnee

        val name = intent.getStringExtra("name")
        val receiverId = intent.getIntExtra("id", 0)

       // senderRoom = receiverId + senderId
       // receiverRoom = senderId + receiverId
        supportActionBar?.title = name //to have the receiver name on top of your screen

        messageRecyclerView = findViewById(R.id.messageRecycleView)
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendBtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, sqliteHelper)
        messageRecyclerView.adapter = messageAdapter

        //add data to recycleView

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderId, receiverId)
            val messageObjectReverse = Message(message, receiverId, senderId)

            sqliteHelper.newMessage(messageObject)
            messageAdapter.add(receiverId)
            sqliteHelper.newMessage(messageObjectReverse)
            messageAdapter.add(senderId)
            messageBox.setText("")
        }
    }


}

