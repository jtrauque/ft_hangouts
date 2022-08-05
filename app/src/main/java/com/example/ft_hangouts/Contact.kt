package com.example.ft_hangouts
import java.util.*

data class Contact (
    var id : Int = getAutoId() + 1,
    var name : String = "",
    var phone: String = "",
    var address: String = "Not provided",
    var mail: String = "Not provided",
    var birth : String = "Not provided"
){
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}