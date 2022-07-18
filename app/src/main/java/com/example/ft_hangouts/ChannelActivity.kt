package com.example.ft_hangouts

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChannelActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var db : DataBaseHandler //

    val receiverRoom: String? = null
    val senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverId = intent.getStringExtra("id")
        val phone = intent.getStringExtra("phone")

       val senderId = null // a lier avec le base de donnee
       // senderRoom = receiverId + senderId
        // receiverRoom = senderId + receiverId
        supportActionBar?.title = name //to have the receiver name on top of your screen

        messageRecyclerView = findViewById(R.id.messageRecycleView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendBtn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderId)

            //db.addmessage to db
        }
    }
}