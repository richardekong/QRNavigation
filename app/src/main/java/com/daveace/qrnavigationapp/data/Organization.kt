package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.NoArgs.Companion.newInstance

data class Organization(
    val id: Int = 0,
    val name: String = "",
    val address: Address = newInstance(Address::class),
    val user: User = newInstance(User::class),
    val logoURL: String = "",
    val websiteURL: String = "",
    val headerBackground: String = "",
    val footerBackground: String = "",
    val events: List<Event> = emptyList(),
    val spaces: List<Space> = emptyList()
)
