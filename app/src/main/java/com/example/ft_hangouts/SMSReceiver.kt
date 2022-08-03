
package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class SMSReceiver : BroadcastReceiver() {
    private lateinit var sqliteHelper: DataBaseHandler

    companion object {
        private val TAG by lazy { SMSReceiver::class.java.simpleName }
    }

    override fun onReceive(context: Context?, intent: Intent) {
        Log.e("ON RECEIVE:", "0")
        Log.e(TAG, "onReceive with $intent")
        sqliteHelper = DataBaseHandler(context!!)

        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }

            val newMessage = Message(
                extractMessages[0].displayMessageBody,
                sqliteHelper.getID(extractMessages[0].displayOriginatingAddress),
                0
            )
            sqliteHelper.newMessage(newMessage)
        }
    }
}
