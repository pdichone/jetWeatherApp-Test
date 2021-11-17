package com.bawp.jetweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bawp.jetweatherapp.screens.main.MainScreen
import com.bawp.jetweatherapp.screens.main.MainViewModel
import com.bawp.jetweatherapp.ui.theme.JetWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetWeatherAppTheme {
                val viewModel by viewModels<MainViewModel>()
                WeatherApp(viewModel = viewModel)
                // A surface container using the 'background' color from the theme
               
            }
        }
    }
}
@Composable
fun WeatherApp(viewModel: MainViewModel) {
    
    Surface(color = MaterialTheme.colors.background) {

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            MainScreen(viewModel)
        }
        
        
    }
    
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetWeatherAppTheme {

    }
}