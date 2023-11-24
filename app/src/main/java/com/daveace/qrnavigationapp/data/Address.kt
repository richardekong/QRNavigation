package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.NoArgs.Companion.newInstance

data class Address(
    val id: Int = 0,
    val description: String = "",
    val location: Location = newInstance(Location::class),
    val postcode: String = "",
    val organization: Organization = newInstance(Organization::class)
)