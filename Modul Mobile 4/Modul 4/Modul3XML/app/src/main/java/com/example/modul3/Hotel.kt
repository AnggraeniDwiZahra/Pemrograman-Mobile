package com.example.modul3

data class Hotel (
    val name: String,
    val stars: String,
    val location: String,
    val price: String,
    val images: List<Int>
) {
    val imageURL: Int get() = images[0]
}
