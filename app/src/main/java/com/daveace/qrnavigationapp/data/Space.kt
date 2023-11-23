package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Space(
    val id:Int = 0,
    val name:String = "",
    val description:String = "",
    val photoURL:String = "",
    val organization: Organization = newInstance(Organization::class) as Organization,
    val addressId:Int = 0,
    val typeId:Int = 0,
    val subspaces:List<Subspace> = listOf()
)