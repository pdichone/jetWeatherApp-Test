package com.bawp.jetweatherapp.data

import androidx.room.*
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.models.MCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * from favorites_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from cities_tbl")
    fun getCities(): Flow<List<MCity>>


    @Query("SELECT * from favorites_tbl where city =:city")
    suspend fun getFavById(city: String): Favorite

    @Query("SELECT * from cities_tbl where city =:city")
    suspend fun getCityById(city: String): MCity



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(mCity: MCity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from favorites_tbl")
    suspend fun deleteAllFavorites()

    @Query("DELETE from cities_tbl")
    suspend fun deleteAllCities()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteCity(mCity: MCity)

}