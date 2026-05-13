package com.example.modul3compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modul3compose.ui.theme.Modul3ComposeTheme

class HotelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Modul3ComposeTheme {
                    val myFactory = HotelViewModelFactory("PROMO-PELAJAR-2026")
                    val hotelViewModel: HotelViewModel = viewModel(factory = myFactory)
                    HotelApp(viewModel = hotelViewModel)
                }
            }
        }
    }
}