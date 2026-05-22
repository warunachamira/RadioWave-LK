package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.FavoriteStation

@Database(entities = [FavoriteStation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
}
