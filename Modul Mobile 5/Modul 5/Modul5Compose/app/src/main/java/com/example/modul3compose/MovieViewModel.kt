package com.example.modul3compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modul3compose.data.MovieEntity
import com.example.modul3compose.data.MovieRepository
import com.example.modul3compose.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _moviesState = MutableStateFlow<Resource<List<MovieEntity>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<MovieEntity>>> = _moviesState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<MovieEntity?>(null)
    val selectedMovie: StateFlow<MovieEntity?> = _selectedMovie.asStateFlow()

    private val _trailerUrl = MutableStateFlow<String?>(null)
    val trailerUrl: StateFlow<String?> = _trailerUrl.asStateFlow()

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collect { result ->
                _moviesState.value = result

                when (result) {
                    is Resource.Loading -> Timber.d("DATA_LIST: Sedang memuat data film dari API/Cache...")
                    is Resource.Success -> Timber.d("DATA_LIST: Berhasil memuat ${result.data.size} film.")
                    is Resource.Error -> Timber.e("DATA_LIST: Gagal memuat data terbaru. ${result.message}")
                }
            }
        }
    }

    fun getMovieById(id: Int?): MovieEntity? {
        val state = _moviesState.value
        return if (state is Resource.Success) {
            state.data.find { it.id == id }
        } else {
            null
        }
    }

    fun onMovieClicked(movie: MovieEntity) {
        _selectedMovie.value = movie
        Timber.i("NAVIGASI: User memilih film ${movie.title} untuk melihat detail.")
    }

    fun loadTrailer(movieId: Int) {
        viewModelScope.launch {
            try {
                _trailerUrl.value = repository.getTrailerUrl(movieId)
            } catch (e: Exception) {
                Timber.e(e, "Gagal mengambil trailer")
                _trailerUrl.value = null
            }
        }
    }

    suspend fun getTrailerUrl(movieId: Int): String? {
        return repository.getTrailerUrl(movieId)
    }
}