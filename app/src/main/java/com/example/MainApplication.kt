package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.StationRepository

class MainApplication : Application() {
    lateinit var database: AppDatabase
        private set
    
    lateinit var repository: StationRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "radio_database"
        ).build()
        repository = StationRepository(database.stationDao())
    }
}
