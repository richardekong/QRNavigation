package com.daveace.qrnavigationapp.data

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi

data class Content(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val event: Event? = null,
    val space: Space? = null,
    val subspace: Subspace? = null
)



