package com.daveace.qrnavigationapp.data

data class Location (
    val id:Int = 0,
    val latitude:Double = 0.0,
    val longitude:Double = 0.0,
    val address:Address? = null
)