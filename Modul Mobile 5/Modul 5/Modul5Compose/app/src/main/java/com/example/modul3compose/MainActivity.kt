package com.example.modul3compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.modul3compose.data.AppDatabase
import com.example.modul3compose.data.MovieRepository
import com.example.modul3compose.data.NetworkClient
import com.example.modul3compose.ui.theme.Modul3ComposeTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val apiKey = "4b2fdca754cffeeb9c5c04c46b62bd10"

    private val viewModel: MovieViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MovieRepository(NetworkClient.apiService, database.movieDao(), apiKey)
        MovieViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(
            "app_prefs",
            MODE_PRIVATE
        )

        val savedLanguage = prefs.getString(
            "language",
            "en"
        ) ?: "en"

        val locale = java.util.Locale(savedLanguage)
        java.util.Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )

        setContent {
            Modul3ComposeTheme {
                MovieApp(viewModel = viewModel)
            }
        }

        Timber.d("DEBUG_LOG: MainActivity initialized successfully with TMDB API Integration")
    }
}