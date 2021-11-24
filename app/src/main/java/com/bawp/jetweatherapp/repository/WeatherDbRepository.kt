package com.bawp.jetweatherapp.repository

import com.bawp.jetweatherapp.data.WeatherDao
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
    ) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)
    suspend fun getUnitById(city: String): Unit = weatherDao.getUnitById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
}