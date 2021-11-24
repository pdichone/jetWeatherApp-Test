package com.bawp.jetweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}