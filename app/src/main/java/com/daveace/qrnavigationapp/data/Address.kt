package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Address(
    val id: Int = 0,
    val description: String = "",
    val location: Location = newInstance(Location::class) as Location,
    val postcode: String = "",
    val organization: Organization = newInstance(Organization::class) as Organization
)