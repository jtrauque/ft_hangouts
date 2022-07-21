package com.example.ft_hangouts

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

import java.util.*

data class Message (
    var message: String = "",
    var senderId: Int = 0,
    var receiverId: Int = 0,
    var messageId : Int = getAutoId(),
    var date: String = getDate()
) {

    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }

        @SuppressLint("NewApi")
        fun getDate(): String {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            return sdf.format(Date())
        }
    }
}