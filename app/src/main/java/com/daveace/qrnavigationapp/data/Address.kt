package com.daveace.qrnavigationapp.data

data class Address(
    val id: Int = 0,
    val description: String = "",
    val location: Location? = null,
    val postcode: String = "",
    val organization: Organization? = null
)