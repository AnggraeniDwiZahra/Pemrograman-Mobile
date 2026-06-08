package com.example.modul3compose.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val apiKey: String
) {
    fun getPopularMovies(): Flow<Resource<List<MovieEntity>>> = flow {
        emit(Resource.Loading)

        try {
            val response = apiService.getPopularMovies(apiKey = apiKey)
            val remoteMovies = response.results
            val movieEntities = remoteMovies.map { dto: MovieDto ->
                MovieEntity(
                    id = dto.id,
                    title = dto.title,
                    overview = dto.overview,
                    posterPath = dto.posterPath,
                    releaseDate = dto.releaseDate,
                    voteAverage = dto.voteAverage
                )
            }

            movieDao.clearAllMovies()
            movieDao.insertMovies(movieEntities)

        } catch (e: Exception) {
            emit(Resource.Error(message = "Gagal memuat data terbaru. Menampilkan data lokal.", throwable = e))
        }

        movieDao.getAllMovies().collect { localMovies ->
            emit(Resource.Success(localMovies))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTrailerUrl(movieId: Int): String? {
        val response = apiService.getMovieVideos(
            movieId = movieId,
            apiKey = apiKey
        )

        val trailer = response.results.firstOrNull {
            it.site == "YouTube" &&
                    it.type == "Trailer"
        }

        return trailer?.let {
            "https://www.youtube.com/watch?v=${it.key}"
        }
    }
}