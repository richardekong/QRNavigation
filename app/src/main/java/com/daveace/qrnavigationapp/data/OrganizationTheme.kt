package com.daveace.qrnavigationapp.data

data class OrganizationTheme(val organization: Organization) {
    val name = organization.name
    val logoURL = organization.logoURL
    val headerBackground = organization.headerBackground
    val footerBackground = organization.footerBackground
    val websiteURL = organization.websiteURL
}