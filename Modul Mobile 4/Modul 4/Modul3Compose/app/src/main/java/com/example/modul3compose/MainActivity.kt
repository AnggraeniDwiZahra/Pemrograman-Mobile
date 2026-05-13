package com.example.modul3compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.modul3compose.ui.theme.Modul3ComposeTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val viewModel: HotelViewModel by viewModels {
        HotelViewModelFactory("PROMO_PELAJAR")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Modul3ComposeTheme {
                HotelApp(viewModel = viewModel)
            }
        }

        Timber.d("DEBUG_LOG: MainActivity initialized successfully with ComponentActivity")
        Timber.i("PROMO_CODE: Promo PELAJAR has been applied to ViewModel")
    }
}