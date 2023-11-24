package com.daveace.qrnavigationapp.data

import androidx.annotation.DrawableRes


data class Note(
    @DrawableRes
    val icon: Int = 0,
    val texts: List<String> = emptyList()
)