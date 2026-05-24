package com.example.dynamiclist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ItemModel (
    val id: Int,
    val judul: String,
    val deskripsi: String,
    initialSwitchState: Boolean = false
) {
    var isSwitchOn by mutableStateOf(initialSwitchState)
}