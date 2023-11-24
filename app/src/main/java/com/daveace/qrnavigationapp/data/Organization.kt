package com.daveace.qrnavigationapp.data

data class Organization(
    val id: Int = 0,
    val name: String = "",
    val address: Address? = null,
    val user: User? = null,
    val logoURL: String = "",
    val websiteURL: String = "",
    val headerBackground: String = "",
    val footerBackground: String = "",
    val events: List<Event> = emptyList(),
    val spaces: List<Space> = emptyList()
)
