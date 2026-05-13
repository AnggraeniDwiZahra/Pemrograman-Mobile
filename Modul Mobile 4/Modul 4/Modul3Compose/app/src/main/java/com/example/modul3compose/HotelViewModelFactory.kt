package com.example.modul3compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HotelViewModelFactory(private val promo: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HotelViewModel(promo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}