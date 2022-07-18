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
    lateinit var sqliteHelper: DataBaseHandler

    val receiverRoom: Int = 0
    val senderRoom: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        val name = intent.getStringExtra("name")
        val receiverId = intent.getIntExtra("id", 0)
        val phone = intent.getStringExtra("phone")

        sqliteHelper = DataBaseHandler(this)
        val senderId: Int = 0 // a lier avec le base de donnee
        // senderRoom = receiverId + senderId
        // receiverRoom = senderId + receiverId




        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderId, receiverId)

            sqliteHelper.newMessage(messageObject)
        }
    }
}

