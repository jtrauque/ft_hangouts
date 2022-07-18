package com.example.ft_hangouts

import java.util.*

data class Message (
    var message: String = "",
    var senderId: Int = 0,
    var receiverId: Int = 0,
    var messageId : Int = getAutoId()
) {

    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}