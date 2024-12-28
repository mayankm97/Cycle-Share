package com.example.cycleshare.rentOut

data class RentOutAttr(
    val id: String = "",
    val username: String,
    val cycleName: String = "",
    val imageUrls: List<String> = emptyList(), // List of image URLs as strings
    val isGearCycle: Boolean = false, // Represents the switch for gear cycle
    val rentAmount: Int = 0 // Rent amount for the cycle
)
