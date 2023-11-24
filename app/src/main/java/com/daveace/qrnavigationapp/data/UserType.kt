package com.daveace.qrnavigationapp.data

data class UserType(
    val id:Int = 0,
    val name:String = "",
    val user: User? = null
) {

    enum class Type(private val type:String){
        ADULT("Adult"),
        CHILD("Child"),
        STUDENT("Student"),
        STAFF("Staff");

        fun type():String = type
    }
}