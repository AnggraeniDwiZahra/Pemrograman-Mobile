package com.example.modul3compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class HotelViewModel(val promoCode: String) : ViewModel() {
    private val _listHotel = MutableStateFlow(
        listOf(
            Hotel("Kollektiv Hotel", "⭐⭐⭐", "Sukajadi, Bandung", "Rp423.649", R.drawable.kollektiv),
            Hotel("eL Hotel Bandung", "⭐⭐⭐⭐", "Merdeka, Bandung", "Rp724.054", R.drawable.elhotel),
            Hotel("Geary Hotel Bandung", "⭐⭐⭐", "Pasirkaliki, Bandung", "Rp387.061", R.drawable.geary),
            Hotel("Pullman Bandung Grand Central", "⭐⭐⭐⭐⭐", "Cibeunying, Bandung", "Rp1.652.504", R.drawable.pullman),
            Hotel("Grandia Hotel Bandung", "⭐⭐⭐⭐", "Cihampelas, Bandung", "Rp532.350", R.drawable.grandia)
        )
    )
    val listHotel: StateFlow<List<Hotel>> = _listHotel.asStateFlow()

    private val _selectedHotel = MutableStateFlow<Hotel?>(null)
    val selectedHotel: StateFlow<Hotel?> = _selectedHotel.asStateFlow()

    fun getHotelByName(name: String?): Hotel? {
        return _listHotel.value.find { it.name == name }
    }

    init {
        Timber.d("DATA_LIST: Berhasil memuat ${_listHotel.value.size} hotel ke dalam list.")
    }

    fun onHotelClicked(hotel: Hotel) {
        _selectedHotel.value = hotel

        Timber.i("NAVIGASI: User memilih hotel ${hotel.name} untuk melihat detail.")

    }
}