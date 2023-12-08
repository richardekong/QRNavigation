package com.daveace.qrnavigationapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
data class Event(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val organizer: Organization? = null,
    val start: LocalDateTime = LocalDateTime.now(),
    val end: LocalDateTime = LocalDateTime.now(),
    val imageURL: String = "",
    val venues: List<Venue> = emptyList()
)