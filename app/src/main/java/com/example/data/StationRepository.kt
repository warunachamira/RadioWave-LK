package com.example.data

import com.example.model.Station
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.model.FavoriteStation

class StationRepository(private val stationDao: StationDao) {
    
    // Combining hardcoded local stations with dynamic favorites from Room
    fun getAllStations(): Flow<List<Station>> {
        return stationDao.getFavorites().map { favorites ->
            val favoriteIds = favorites.map { it.stationId }.toSet()
            StationList.ALL_STATIONS.map { station ->
                station.copy(isFavorite = favoriteIds.contains(station.id))
            }
        }
    }

    suspend fun toggleFavorite(stationId: String, isFavoriteNow: Boolean) {
        if (!isFavoriteNow) {
            stationDao.addFavorite(FavoriteStation(stationId))
        } else {
            stationDao.removeFavorite(stationId)
        }
    }
}
