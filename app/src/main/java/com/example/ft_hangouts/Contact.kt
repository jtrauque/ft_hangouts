package com.example.ft_hangouts
import java.util.*

data class Contact (
    var id : Int = getAutoId() + 1,
    var name : String = "",
    var phone: String = ""
){
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}