package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi

data class Venue(
    val id:Int = 0,
    val event:Event? = null,
    val spaceId :Int = 0,
    val subspaceId:Int = 0
)