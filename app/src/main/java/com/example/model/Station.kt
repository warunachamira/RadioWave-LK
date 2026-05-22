package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Station(
    val id: String,
    val name: String,
    val streamUrl: String,
    val logoUrl: String,
    val language: String,
    val category: String,
    val city: String = "Colombo",
    val isFavorite: Boolean = false
)

@Entity(tableName = "favorites")
data class FavoriteStation(
    @PrimaryKey val stationId: String,
    val timestamp: Long = System.currentTimeMillis()
)
