package com.bawp.jetweatherapp.di

import android.content.Context
import androidx.room.Room
import com.bawp.jetweatherapp.data.UnitModeDataStore
import com.bawp.jetweatherapp.data.UnitModeImpl
import com.bawp.jetweatherapp.data.WeatherDao
import com.bawp.jetweatherapp.data.WeatherDatabase
import com.bawp.jetweatherapp.network.WeatherApi
import com.bawp.jetweatherapp.repository.WeatherRepository
import com.bawp.jetweatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): UnitModeImpl {
        return UnitModeDataStore(context)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi) = WeatherRepository(api)

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao
            = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase
            = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_db")
        .fallbackToDestructiveMigration()
        .build()
}