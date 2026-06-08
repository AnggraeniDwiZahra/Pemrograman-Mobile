package com.example.modul3compose

import androidx.lifecycle.ViewModel

class LanguageVIewModel : ViewModel() {
    val LanguageList = listOf(
        LanguageItem("Bahasa Indonesia", "id"),
        LanguageItem("English", "en"),
    )
}

data class LanguageItem(
    val name: String,
    val code: String
)