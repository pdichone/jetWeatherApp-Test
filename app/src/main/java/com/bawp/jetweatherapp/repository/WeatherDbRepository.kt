package com.bawp.jetweatherapp.repository

import androidx.room.*
import com.bawp.jetweatherapp.data.WeatherDao
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.MCity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
    ) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    fun getCities(): Flow<List<MCity>> = weatherDao.getCities()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)
    suspend fun getCityById(city: String): MCity = weatherDao.getCityById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun insertCity(mCity: MCity) = weatherDao.insertCity(mCity)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteAllCities() = weatherDao.deleteAllCities()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteCity(mCity: MCity) = weatherDao.deleteCity(mCity)
}