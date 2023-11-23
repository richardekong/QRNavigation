package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Organization(
    val id: Int = 0,
    val name: String = "",
    val address: Address = newInstance(Address::class) as Address,
    val user: User = newInstance(User::class) as User,
    val logoURL: String = "",
    val websiteURL: String = "",
    val headerBackground: String = "",
    val footerBackground: String = "",
    val events: List<Event> = listOf(),
    val spaces: List<Space> = listOf()
)
