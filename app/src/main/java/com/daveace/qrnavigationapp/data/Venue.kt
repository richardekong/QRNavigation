package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

@RequiresApi(O)
data class Venue(
    val id:Int = 0,
    val event:Event = newInstance(Event::class) as Event,
    val spaceId :Int = 0,
    val subspaceId:Int = 0
)