package com.bawp.jetweatherapp.screens.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bawp.jetweatherapp.data.DataOrException
import com.bawp.jetweatherapp.model.WeatherObject

import com.bawp.jetweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val repository: WeatherRepository)
    :ViewModel(){


    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>>
            = mutableStateOf(DataOrException(null, true,Exception("")))

    init {
        getWeather("Spokane")
    }
    private fun getWeather(city: String) {
        viewModelScope.launch {
             data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("GET", "getWeather: ${data.value.data.toString()}")

    }

}