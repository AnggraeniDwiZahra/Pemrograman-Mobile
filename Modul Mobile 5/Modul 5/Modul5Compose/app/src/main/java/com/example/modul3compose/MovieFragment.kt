package com.example.modul3compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modul3compose.data.AppDatabase
import com.example.modul3compose.data.MovieRepository
import com.example.modul3compose.data.NetworkClient
import com.example.modul3compose.ui.theme.Modul3ComposeTheme

class MovieFragment : Fragment() {

    private val apiKey = "4b2fdca754cffeeb9c5c04c46b62bd10"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Modul3ComposeTheme {
                    val context = requireContext().applicationContext
                    val database = AppDatabase.getDatabase(context)
                    val repository = MovieRepository(NetworkClient.apiService, database.movieDao(), apiKey)

                    val myFactory = MovieViewModelFactory(repository)
                    val movieViewModel: MovieViewModel = viewModel(factory = myFactory)

                    MovieApp(viewModel = movieViewModel)
                }
            }
        }
    }
}