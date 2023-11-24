package com.daveace.qrnavigationapp.data

data class Subspace(
    val id:Int = 0,
    val name:String = "",
    val description:String ="",
    val photoURL:String = "",
    val space: Space? = null,
    val typeId:Int = 0
)