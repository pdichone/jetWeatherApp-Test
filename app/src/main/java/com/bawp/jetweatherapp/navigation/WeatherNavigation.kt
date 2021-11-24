package com.bawp.jetweatherapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.bawp.jetweatherapp.screens.screens.about.AboutScreen
import com.bawp.jetweatherapp.screens.screens.favorites.FavoritesScreen
import com.bawp.jetweatherapp.screens.screens.main.MainScreen
import com.bawp.jetweatherapp.screens.screens.search.SearchScreen
import com.bawp.jetweatherapp.screens.screens.search.SearchViewModel
import com.bawp.jetweatherapp.screens.screens.settings.SettingsScreen
import com.bawp.jetweatherapp.screens.screens.splash.WeatherSplashScreen

@ExperimentalComposeUiApi
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

        //val searchName = WeatherScreens.SearchScreen.name
        composable(WeatherScreens.SearchScreen.name){
                       val viewModel = hiltViewModel<SearchViewModel>()
                       SearchScreen(navController = navController)
                   }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
                  arguments = listOf(
                      navArgument("city"){
                          type = NavType.StringType
                      })) { navBack ->

              navBack.arguments?.getString("city").let {

                  val viewModel = hiltViewModel<SearchViewModel>()
                  MainScreen(viewModel = viewModel,
                      navController = navController,
                            city = it.toString())
              }
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name){
            FavoritesScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController = navController)
        }
    }
}