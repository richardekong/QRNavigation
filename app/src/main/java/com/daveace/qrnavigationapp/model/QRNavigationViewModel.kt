package com.daveace.qrnavigationapp.model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.data.Address
import com.daveace.qrnavigationapp.data.Event
import com.daveace.qrnavigationapp.data.Location
import com.daveace.qrnavigationapp.data.Note
import com.daveace.qrnavigationapp.data.Organization
import com.daveace.qrnavigationapp.data.Space
import com.daveace.qrnavigationapp.data.Subspace
import com.daveace.qrnavigationapp.data.User
import com.daveace.qrnavigationapp.data.Venue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month

class QRNavigationViewModel() : ViewModel() {

    //Todo: this will be replace by an elaborate api call
    fun organizations() = listOf(
        Organization(
            id = 1,
            name = "Cardiff University",
            address = Address(id = 1),
            logoURL = "https://media.glassdoor.com/sql/147776/cardiff-university-squarelogo.png",
            websiteURL = "https://www.cardiff.ac.uk",
            headerBackground = "#020245", footerBackground = "#020245",
            events = listOf(Event(id = 1)),
            spaces = listOf(Space(id = 1))
        ),
        Organization(
            id= 2,
            name = "Sky",
            address = Address(id = 2),
            logoURL = "https://media.glassdoor.com/sql/3903/sky-squarelogo-1593521823822.png",
            websiteURL = "https://www.sky.com",
            headerBackground = "#001159",
            footerBackground = "#001159",
            events = listOf(Event(id = 2)),
            spaces = listOf(Space(id = 2))
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
            name = "Ovo",
            logoURL = "https://media.glassdoor.com/sqlm/767881/ovo-energy-squarelogo-1474642219968.png"
        )
    )

    fun locations() = listOf(
        Location(id = 1, latitude = 51.489014, longitude = -3.179717, address = Address(id = 1)),
        Location(id = 2, latitude = 51.484414, longitude = -0.331331, address = Address(id = 2)),
    )

    fun addresses() = listOf(
        Address(id=1, description="Cardiff University Main Building", location = Location(id=1), postcode="CF10 3AT", organization = Organization(id=1)),
        Address(id=2, description="1 Grant Way, Osterley, Isleworth, England, United Kingdom", postcode="TW7 5QD", location = Location(id=2), organization = Organization(id=2))
    )

    fun spaces() = listOf(
        Space(
            id = 1,
            name = "Cardiff University",
            description = "Main Building",
            organization = Organization(id = 1),
            photoURL = "https://shorturl.at/djpGR",
            addressId = 1,
            typeId = 1,
            subspaces = listOf(
                Subspace(
                    id = 1, name = "Reception", photoURL = "", space = Space(id = 1),
                    description = "Reception for main Cardiff University Main Building", typeId = 2
                )
            )
        ),
        Space(
            id = 2,
            name = "Sky Corporate Head Office",
            description = "Head office",
            organization = Organization(id = 2),
            photoURL = "https://shorturl.at/cltK3",
            addressId = 2,
            typeId = 1,
            subspaces = listOf(
                Subspace(
                    id = 2, name = "News room", description = "Room for saying the news",
                    photoURL = "https://shorturl.at/aqDG6", space = Space(id = 2), typeId = 2
                )
            )
        )
    )

    fun events() = listOf(
        Event(
            id = 2, name = "Breakfast show", description = "Show", organizer = Organization(id = 2),
            start = LocalDateTime.of(LocalDate.of(2023, Month.DECEMBER, 20), LocalTime.of(9, 0, 0)),
            end = LocalDateTime.of(LocalDate.of(2023, Month.DECEMBER, 20), LocalTime.of(12, 0, 0)),
            venues = listOf(
                Venue(id = 1, event = Event(id = 1), spaceId = 2, subspaceId = 2)
            )
        ),
    )

    fun currentOrganization(name: String = "Sky"): Organization? =
        //todo: this block of code will be replaced by a call to an external web API
        organizations().find { name.equals(it.name, true) }

    fun welcomeNotes(ctx: Context) = listOf(
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