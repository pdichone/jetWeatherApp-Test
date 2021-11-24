package com.bawp.jetweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import com.bawp.jetweatherapp.data.UnitModeImpl
import com.bawp.jetweatherapp.navigation.WeatherNavigation
import com.bawp.jetweatherapp.ui.theme.JetWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Icon attribution: <div>Icons made by <a href="https://www.flaticon.com/authors/good-ware" title="Good Ware">Good Ware</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 */
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetWeatherAppTheme {
                //val viewModel by viewModels<MainViewModel>()
                WeatherApp()
            }
        }
    }
}
@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun WeatherApp() {

    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(), content = {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherNavigation()
            }
        })
    
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetWeatherAppTheme {

    }
}