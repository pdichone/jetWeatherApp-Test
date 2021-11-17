package com.bawp.jetweatherapp.utils

import com.bawp.jetweatherapp.BuildConfig


object Constants {
    //using this plugin: https://github.com/google/secrets-gradle-plugin
    private const val apiKey = BuildConfig.apiKey

    /**
     *  how to use weather condition codes:
     *  https://openweathermap.org/weather-conditions
     */
    //api_key = "ed60fcfbd110ee65c7150605ea8aceea"
    //full url: https://api.openweathermap.org/data/2.5/forecast?q=spokane&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial
    //base: https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = apiKey
}