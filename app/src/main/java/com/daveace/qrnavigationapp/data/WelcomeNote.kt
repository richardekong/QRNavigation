package com.daveace.qrnavigationapp.data

import android.content.Context
import androidx.annotation.DrawableRes
import com.daveace.qrnavigationapp.R

fun  welcomeNotes(ctx: Context) = listOf(
    WelcomeNote(
        icon = R.drawable.qr_code_icon,
        texts = listOf(
            ctx.getString(R.string.connect_to_organization),
            ctx.getString(R.string.read_qr_codes),
        )
    ),
    WelcomeNote(
        icon = R.drawable.loc_marker,
        texts = listOf(
            ctx.getString(R.string.get_general_description_about_a_space),
            ctx.getString(R.string.get_directions_to_an_organization_s_spaces)
        )
    ),
    WelcomeNote(
        icon = R.drawable.event_icon,
        texts = listOf(
            ctx.getString(R.string.get_general_information_about_scheduled_events),
            ctx.getString(R.string.get_general_information_about_venues)
        )
    )
)

data class WelcomeNote(
    @DrawableRes val icon: Int, val texts: List<String>
)