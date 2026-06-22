package com.example.gardenmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.gardenmate.data.AppDatabase
import com.example.gardenmate.data.NetworkModule
import com.example.gardenmate.data.Plant
import com.example.gardenmate.data.WeatherResponse
import com.example.gardenmate.ui.theme.GardenMateTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val plantDao = database.plantDao()

        setContent {
            GardenMateTheme {
                var weatherState by remember { mutableStateOf<WeatherResponse?>(null) }
                val plantListState by plantDao.getAllPlants().collectAsState(initial = emptyList())

                LaunchedEffect(Unit) {
                    try {
                        val response = NetworkModule.apiService.getCurrentWeather(
                            apiKey = "8a2a67203adb4380af813823261906",
                            location = "Banjarmasin"
                        )
                        weatherState = response
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        weather = weatherState,
                        plants = plantListState,
                        onAddPlant = { name: String, type: String, interval: Int ->
                            lifecycleScope.launch {
                                val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
                                val newPlant = Plant(
                                    name = name,
                                    type = type,
                                    wateringInterval = interval,
                                    lastWatered = currentDate
                                )
                                plantDao.insertPlant(newPlant)
                            }
                        },
                        onDeletePlant = { plant: Plant ->
                            lifecycleScope.launch {
                                plantDao.deletePlant(plant)
                            }
                        },
                        onWaterPlant = { plantId ->
                            lifecycleScope.launch {
                                val todayDateTime = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())
                                val log = com.example.gardenmate.data.WateringLog(
                                    plantId = plantId,
                                    wateredAt = todayDateTime
                                )
                                plantDao.insertLog(log)

                                val currentPlant = plantListState.find { it.id == plantId }
                                if (currentPlant != null) {
                                    val updatePlant = currentPlant.copy(lastWatered = todayDateTime)
                                    plantDao.insertPlant(updatePlant)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}