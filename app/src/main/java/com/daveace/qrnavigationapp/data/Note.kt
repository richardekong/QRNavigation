package com.daveace.qrnavigationapp.data

import androidx.annotation.DrawableRes


data class Note(
    @DrawableRes val icon: Int, val texts: List<String>
)