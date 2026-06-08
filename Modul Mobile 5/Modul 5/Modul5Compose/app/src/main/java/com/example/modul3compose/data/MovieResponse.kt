package com.example.modul3compose.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse (
    @SerialName("results")
    val results: List<MovieDto>
)

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage: Double
)

@Serializable
data class VideoResponse(
    @SerialName("results")
    val results: List<VideoDto>
)

@Serializable
data class VideoDto(
    @SerialName("key")
    val key: String,
    @SerialName("name")
    val name: String,
    @SerialName("site")
    val site: String,
    @SerialName("type")
    val type: String
)