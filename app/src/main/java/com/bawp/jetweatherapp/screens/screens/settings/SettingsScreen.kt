package com.bawp.jetweatherapp.screens.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bawp.jetweatherapp.components.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController) {
   Scaffold(topBar = {
       WeatherAppBar(
           title = "Settings",
           icon = Icons.Default.ArrowBack,
           isMainScreen = false,
           navController = navController,){
           navController.popBackStack()
       }
   }) {
       Surface(modifier = Modifier
           .fillMaxWidth()
           .fillMaxHeight()) {
           Column(
               verticalArrangement = Arrangement.SpaceAround,
               horizontalAlignment = Alignment.CenterHorizontally
                 ) {
               Text("Change Units of Measurement")
               //todo: radio button with metric(celsius and meter/sec), imperial(fahrenheit & mph)

           }

       }
   }
}
