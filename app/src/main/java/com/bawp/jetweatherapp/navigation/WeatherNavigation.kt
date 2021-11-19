package com.bawp.jetweatherapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.bawp.jetweatherapp.screens.screens.main.MainScreen
import com.bawp.jetweatherapp.screens.screens.main.MainViewModel
import com.bawp.jetweatherapp.screens.screens.splash.WeatherSplashScreen

@ExperimentalCoilApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController,
    startDestination = WeatherScreens.SplashScreen.name ){
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(viewModel = viewModel,navController = navController,)
        }
    }
}