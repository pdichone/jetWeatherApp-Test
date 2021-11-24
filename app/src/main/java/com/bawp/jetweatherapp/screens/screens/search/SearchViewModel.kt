package com.bawp.jetweatherapp.screens.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bawp.jetweatherapp.data.DataOrException
import com.bawp.jetweatherapp.model.WeatherObject
import com.bawp.jetweatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: WeatherRepository):
    ViewModel() {

    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>>
            = mutableStateOf(DataOrException(null, true,Exception("")))

    suspend fun getWeatherData(city: String): DataOrException<WeatherObject, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }


}

//    fun getWeatherData(city: String): DataOrException<WeatherObject, Boolean, Exception> {
//       viewModelScope.launch {
//           data.value = repository.getWeather(cityQuery = city)
//       }
//        return data.value
//        }
//    }