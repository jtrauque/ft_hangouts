package com.example.ft_hangouts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class SmsListener : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SmsListener::class.java.simpleName }
    }
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent) {
       // if(intent.action.equals("android.provider.Telephony.SMS_RECEIVED"))
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {

            // val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            // extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val message = smsMessage.messageBody.toString()
                val phone = smsMessage.originatingAddress
                Log.e("LISTENER M:", message)
                Log.e("LISTENER P:", phone.toString())
            //    Toast.makeText(context, "From $phone, Body : $message", Toast.LENGTH_SHORT).show()
               // intent.putExtra("listenMessage", message)
               // intent.putExtra("listenPhone", phone)
                //context?.startActivity(intent)

               // context?.sendBroadcast(intent)
                //   val num = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            }
        }
    }

//    fun getMessage(intent: Intent) : String {
//        return intent.getStringExtra("listenMessage").toString()
//
//    }
//
//    fun getPhone(intent: Intent) : String {
//        return intent.getStringExtra("listenPhone").toString()
//    }
}

