package com.example.modul3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HotelViewModelFactory(private val promoCode: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java)) {
            return HotelViewModel(promoCode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}