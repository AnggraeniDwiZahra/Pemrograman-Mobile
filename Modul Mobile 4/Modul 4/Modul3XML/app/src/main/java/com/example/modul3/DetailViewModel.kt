package com.example.modul3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel(private val promoCode: String) : ViewModel() {

    private val _promo = MutableLiveData<String>()
    val promo: LiveData<String> get() = _promo

    init {
        _promo.value = promoCode
    }
}