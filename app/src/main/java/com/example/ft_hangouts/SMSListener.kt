package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.util.Log

class SmsListener : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SmsListener::class.java.simpleName }
    }
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent) {

        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            // val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            // extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val message = smsMessage.messageBody
                //   val num = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            }
        }
    }
}

