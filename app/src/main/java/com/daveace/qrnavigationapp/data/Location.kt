package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Location (
    val id:Int = 0,
    val latitude:Double = 0.0,
    val longitude:Double = 0.0,
    val address:Address = newInstance(Address::class) as Address
)