package com.example.harassfree

data class Official(
    val name: String,
    val email: String,
    val photo: Int // Resource ID of the photo (or use a URL if fetching from the internet)
)
