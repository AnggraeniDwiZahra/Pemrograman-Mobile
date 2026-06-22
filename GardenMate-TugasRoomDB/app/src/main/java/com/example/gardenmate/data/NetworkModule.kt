package com.example.gardenmate.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType

object NetworkModule {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val apiService: WeatherApiService by lazy {
        val contentType = MediaType.get("application/json")
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(WeatherApiService::class.java)
    }
}