package com.daveace.qrnavigationapp.data

//The property of this class will be updated latter to reflect resource from the server
data class Organization(
    val name:String,
    val logo: String,
)


fun organizations() = listOf(
    Organization("Cardiff University",  "https://media.glassdoor.com/sql/147776/cardiff-university-squarelogo.png"),
    Organization("Sky",  "https://media.glassdoor.com/sql/3903/sky-squarelogo-1593521823822.png"),
    Organization("HSBC",  "https://media.glassdoor.com/sql/3482/hsbc-holdings-squarelogo.png"),
    Organization("Starling Bank",  "https://media.glassdoor.com/sql/1337967/starling-bank-squarelogo-1602058664929.png"),
    Organization("Innovate Trust", "https://media.glassdoor.com/sql/2571801/innovate-trust-squarelogo-1635340864290.png"),
    Organization("Ovo","https://media.glassdoor.com/sqlm/767881/ovo-energy-squarelogo-1474642219968.png")
)