package com.example.tugasapi


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET

@Serializable
data class ApiResponse(
    val message: String,
    val code: String,
    val data: TaskData
)

@Serializable
data class TaskData(
    val isi_tugas: String
)

interface ApiService {
    @GET("AnggraeniDwiZahra/555a5bcc3d00685eafdb321608141cb6/raw/c3e514cd0933549819816068667b0d486b7914ce/tugas.json")
    suspend fun getTugas(): ApiResponse
}

object NetworkClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/"

    private val json = Json { ignoreUnknownKeys = true }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(ApiService::class.java)
    }
}