package com.example.gardenmate.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current") val current: CurrentData
)

@Serializable
data class CurrentData(
    @SerialName("condition") val condition: ConditionData
)

@Serializable
data class ConditionData(
    @SerialName("text") val text: String
)