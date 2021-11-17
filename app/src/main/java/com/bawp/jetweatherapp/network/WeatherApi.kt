package com.bawp.jetweatherapp.network



import com.bawp.jetweatherapp.model.WeatherObject
import com.bawp.jetweatherapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY // your api key
                              ) : WeatherObject
    //https://api.openweathermap.org/data/2.5/forecast?q=spokane&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial
//    @GET("forecast?units=metric")
//    suspend fun getWeather(
//        @Query("q") query: String,
//        @Query("appid") appid: String,
//                                 ): WeatherItem

//    @GET("onecall?units=metric&exclude=current,minutely,hourly,alerts")
//    suspend fun getFullWeather(
//        @Query("lat") lat: Double,
//        @Query("lon") long: Double,
//        @Query("appid") appid: String,
//                              ): Response<FullWeather>

}