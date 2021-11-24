package com.bawp.jetweatherapp.data

import androidx.room.*
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * from favorites_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>


    @Query("SELECT * from favorites_tbl where city =:city")
    suspend fun getFavById(city: String): Favorite

    @Query("SELECT * from settings_tbl where unit =:unit")
    suspend fun getUnitById(unit: String): Unit



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)



    @Query("DELETE from favorites_tbl")
    suspend fun deleteAllFavorites()

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteUnit(unit: Unit)

}