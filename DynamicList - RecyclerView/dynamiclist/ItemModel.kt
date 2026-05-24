package com.example.dynamiclist

data class ItemModel (
    val id: Int,
    val judul: String,
    val deskripsi: String,
    var isSwitchOn: Boolean = false
)