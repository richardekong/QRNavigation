package com.daveace.qrnavigationapp.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.data.Note
import com.daveace.qrnavigationapp.data.Organization
import com.daveace.qrnavigationapp.data.User

class ViewModel():ViewModel(){

    //This will be replace by an elaborate api call
    fun organizations() = listOf(
        Organization(
            name = "Cardiff University",
            logoURL = "https://media.glassdoor.com/sql/147776/cardiff-university-squarelogo.png"
        ),
        Organization(
            name = "Sky",
            logoURL = "https://media.glassdoor.com/sql/3903/sky-squarelogo-1593521823822.png"
        ),
        Organization(
            name = "HSBC",
            logoURL = "https://media.glassdoor.com/sql/3482/hsbc-holdings-squarelogo.png"
        ),
        Organization(
            name = "Starling Bank",
            logoURL = "https://media.glassdoor.com/sql/1337967/starling-bank-squarelogo-1602058664929.png"
        ),
        Organization(
            name = "Innovate Trust",
            logoURL = "https://media.glassdoor.com/sql/2571801/innovate-trust-squarelogo-1635340864290.png"
        ),
        Organization(
            name=  "Ovo",
            logoURL = "https://media.glassdoor.com/sqlm/767881/ovo-energy-squarelogo-1474642219968.png"
        )
    )

    fun  welcomeNotes(ctx: Context) = listOf(
        Note(
            icon = R.drawable.qr_code_icon,
            texts = listOf(
                ctx.getString(R.string.connect_to_organization),
                ctx.getString(R.string.read_qr_codes),
            )
        ),
        Note(
            icon = R.drawable.loc_marker,
            texts = listOf(
                ctx.getString(R.string.get_general_description_about_a_space),
                ctx.getString(R.string.get_directions_to_an_organization_s_spaces)
            )
        ),
        Note(
            icon = R.drawable.event_icon,
            texts = listOf(
                ctx.getString(R.string.get_general_information_about_scheduled_events),
                ctx.getString(R.string.get_general_information_about_venues)
            )
        )
    )

    fun getUser() = User()
}