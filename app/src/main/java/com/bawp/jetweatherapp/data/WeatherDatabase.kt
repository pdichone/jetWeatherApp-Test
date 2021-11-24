package com.bawp.jetweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.MCity

@Database(entities = [Favorite::class, MCity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}