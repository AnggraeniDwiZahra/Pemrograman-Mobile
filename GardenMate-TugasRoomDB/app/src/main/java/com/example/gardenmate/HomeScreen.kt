package com.example.gardenmate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gardenmate.data.Plant
import com.example.gardenmate.data.WeatherResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weather: WeatherResponse?,
    plants: List<Plant>,
    onAddPlant: (String, String, Int) -> Unit,
    onDeletePlant: (Plant) -> Unit,
    onWaterPlant: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var plantName by remember { mutableStateOf("") }
    var wateringInterval by remember { mutableStateOf("") }

    val jenisOptions = listOf("Tanaman Hias", "Tanaman Obat", "Tanaman Konsumsi", "Lainnya")
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedJenis by remember { mutableStateOf(jenisOptions[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🌱 GardenMate") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Kondisi Cuaca Saat Ini (Banjarmasin)",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (weather != null) {
                            Text(
                                text = weather.current.condition.text,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            Text(
                                text = "Sedang memuat cuaca...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // --- FORM TAMBAH TANAMAN BARU ---
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tambah Tanaman Baru",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // 1. Input Nama
                        OutlinedTextField(
                            value = plantName,
                            onValueChange = { plantName = it },
                            label = { Text("Nama Tanaman") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // 2. Dropdown Pemilihan Jenis Tanaman (Exposed Dropdown Menu)
                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = selectedJenis,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Jenis Tanaman") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false }
                            ) {
                                jenisOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            selectedJenis = option
                                            dropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        // 3. Input Interval Siram
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (plantName.isNotBlank()) {
                                    val intervalOtomatis = when (selectedJenis) {
                                        "Tanaman Hias" -> 1      // Tanaman hias, otomatis 1 hari sekali
                                        "Tanaman Obat" -> 3      // Tanaman obat (kayu lidah buaya dll), otomatis 3 hari sekali
                                        "Tanaman Konsumsi" -> 1  // Tanaman sayur/cabai, otomatis 1 hari sekali
                                        else -> 2                // Sisanya otomatis 2 hari sekali
                                    }

                                    onAddPlant(plantName, selectedJenis, intervalOtomatis)
                                    plantName = ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Simpan Tanaman")
                        }
                    }
                }
            }

            // --- DAFTAR TANAMAN ---
            item {
                Text(
                    text = "Daftar Tanaman Kamu",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            if (plants.isEmpty()) {
                item {
                    Text(
                        text = "Belum ada tanaman. Yuk tambah di atas!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            items(plants) { plant ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = plant.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Jenis: ${plant.type}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Siram setiap: ${plant.wateringInterval} hari sekali", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Terakhir disiram: ${plant.lastWatered}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.height(8.dp))

                            // --- TOMBOL AKSI UTAMA STRIP/STREAK LOG SEKALIGUS MENJALANKAN FOREIGN KEY ---
                            Button(
                                onClick = { onWaterPlant(plant.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Selesai Menyiram", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        IconButton(onClick = { onDeletePlant(plant) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus Tanaman",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}