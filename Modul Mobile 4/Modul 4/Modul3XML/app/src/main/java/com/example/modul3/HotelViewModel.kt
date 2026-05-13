package com.example.modul3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HotelViewModel(private val promoCode: String) : ViewModel() {
    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())

    private val _navigateToDetail = MutableStateFlow<Hotel?>(null)
    val navigateToDetail = _navigateToDetail.asStateFlow()
    val hotels: StateFlow<List<Hotel>> = _hotels.asStateFlow()

    fun onHotelClicked(hotel: Hotel) {
        timber.log.Timber.d("LOG_EVENT: Tombol Detail ditekan. Data terpilih: Name=${hotel.name}, Price=${hotel.price}")

        _navigateToDetail.value = hotel
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
    init {
        loadHotels()
    }

    private fun loadHotels() {
        val list = listOf(
            Hotel("Kollektiv Hotel", "⭐⭐⭐", "Sukajadi, Bandung", "Rp423.649", listOf(R.drawable.kollektiv)),
            Hotel("eL Hotel Bandung", "⭐⭐⭐⭐", "Merdeka, Bandung", "Rp724.054", listOf(R.drawable.elhotel)),
            Hotel("Geary Hotel Bandung","⭐⭐⭐", "Pasirkaliki, Bandung", "Rp387.061", listOf(R.drawable.geary)),
            Hotel("Pullman Bandung Grand Central", "⭐⭐⭐⭐⭐", "Cibeunying, Bandung", "Rp1.652.504", listOf(R.drawable.pullman)),
            Hotel("Grandia Hotel Bandung", "⭐⭐⭐⭐", "Cihampelas, Bandung", "Rp532.350", listOf(R.drawable.grandia))
        )
        _hotels.value = list

        timber.log.Timber.d("LOG_EVENT: Berhasil memuat ${list.size} data hotel ke dalam list.")
    }
}