package com.daveace.qrnavigationapp.data

import com.daveace.qrnavigationapp.data.DefaultInstance.Companion.newInstance

data class Role(
    val id: Int = 0,
    val name: String = "",
    val user: User = newInstance(User::class) as User
) {
    enum class RoleType(private val role: String) {
        SUPER_ADMIN("SUPER_ADMIN"),
        ADMIN("ADMIN"),
        USER("USER");

        fun role(): String = role

    }
}