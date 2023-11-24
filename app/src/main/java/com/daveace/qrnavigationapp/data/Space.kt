package com.daveace.qrnavigationapp.data

data class Space(
    val id:Int = 0,
    val name:String = "",
    val description:String = "",
    val photoURL:String = "",
    val organization: Organization? = null,
    val addressId:Int = 0,
    val typeId:Int = 0,
    val subspaces:List<Subspace> = emptyList()
)