package com.daveace.qrnavigationapp.model

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
)