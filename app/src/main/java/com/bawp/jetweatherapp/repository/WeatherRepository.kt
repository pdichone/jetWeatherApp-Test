package com.bawp.jetweatherapp.repository

import android.util.Log
import com.bawp.jetweatherapp.data.DataOrException
import com.bawp.jetweatherapp.model.WeatherObject
import com.bawp.jetweatherapp.network.WeatherApi
import com.bawp.jetweatherapp.utils.Constants
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    private val dataOrException = DataOrException<WeatherObject, Boolean, Exception>()
    suspend fun getWeather(cityQuery: String): DataOrException<WeatherObject, Boolean, Exception> {
             try {
                 dataOrException.loading = true
                 dataOrException.data = api.getWeather(cityQuery, appid = Constants.API_KEY)
                 Log.d("REX", "getWeather: $dataOrException")
                 if (dataOrException.data.toString().isEmpty()) dataOrException.loading = false


             }catch (exception: Exception){
                 dataOrException.e = exception
                 Log.d("TAG", "getWeather: ${exception.toString()}")
             }
        return dataOrException
    }


}