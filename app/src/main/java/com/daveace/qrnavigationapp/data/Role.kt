package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.NoArgs.Companion.newInstance

data class Role(
    val id: Int = 0,
    val name: String = "",
    val user: User = newInstance(User::class)
) {
    enum class RoleType(private val role: String) {
        SUPER_ADMIN("SUPER_ADMIN"),
        ADMIN("ADMIN"),
        USER("USER");

        fun role(): String = role

    }
}