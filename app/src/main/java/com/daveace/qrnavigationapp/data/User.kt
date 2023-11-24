package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class User(
    val id:Int = 0,
    val username:String = "",
    val password:String = "",
    val age:Int = 0,
    val organization: Organization = newInstance(Organization::class),
    val roles:List<Role> = emptyList(),
    val types:List<UserType> = emptyList(),
    var isAccountExpired:Boolean = false,
    var isCredentialExpired:Boolean = false,
    var isAccountLocked:Boolean = false,
    var isAccountEnabled:Boolean = true
)

