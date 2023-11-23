package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Subspace(
    val id:Int = 0,
    val name:String = "",
    val description:String ="",
    val photoURL:String = "",
    val space: Space = newInstance(Space::class) as Space,
    val typeId:Int = 0
)