package com.bawp.jetweatherapp.screens.screens.main

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.bawp.jetweatherapp.components.WeatherAppBar
import com.bawp.jetweatherapp.data.DataOrException
import com.bawp.jetweatherapp.data.UnitModeImpl
import com.bawp.jetweatherapp.model.WeatherItem
import com.bawp.jetweatherapp.model.WeatherObject
import com.bawp.jetweatherapp.navigation.WeatherScreens
import com.bawp.jetweatherapp.screens.screens.search.SearchViewModel
import com.bawp.jetweatherapp.utils.formatDate
import com.bawp.jetweatherapp.utils.formatDecimals
import com.bawp.jetweatherapp.widgets.HumidityWindPressureRow
import com.bawp.jetweatherapp.widgets.SunsetSunRiseRow
import com.bawp.jetweatherapp.widgets.WeatherDetailRow
import com.bawp.jetweatherapp.widgets.WeatherStateImage
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*


@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController,
    city: String,
              ) {

    val curCity: String = if (city.isBlank()) "Spokane" else city

    val weatherData = produceState<DataOrException<WeatherObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
                                                                                      ) {
        value = viewModel.getWeatherData(city = curCity)
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator(
            color = Color.DarkGray
                                 )
    } else if (weatherData.data != null) {
        MainScaffold(weatherObject = weatherData.data!!,
            navController = navController)

    }

}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun MainScaffold(
    weatherObject: WeatherObject, navController: NavHostController,
                ) {

    Scaffold(topBar = {

        WeatherAppBar(
            title = weatherObject.city.name + ", ${weatherObject.city.country}",
            navController = navController,
            onAddActionClicked = { navController.navigate(WeatherScreens.SearchScreen.name) },
            elevation = 5.dp
                     )


    }, bottomBar = {}, backgroundColor = Color.LightGray.copy(alpha = 0.08f)
            ) {

        MainContent(data = weatherObject)
    }

}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
private fun MainContent(data: WeatherObject?) {
    val imageUrl = "https://openweathermap.org/img/wn/${data!!.list[0].weather[0].icon}.png"
    var unitToggleState by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        Row(modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceEvenly,
           verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatDate(data.list[0].dt), //"Today Nov 29",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
                )
            IconToggleButton(
                checked = !unitToggleState,
                onCheckedChange = {
                unitToggleState = !it

                    Log.d("TAG", "MainContent: $unitToggleState")

            }, ) {
                Text(text = if (unitToggleState) "F" else "C")
            }
        }
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape
               ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFC400))
                    .padding(horizontal = 16.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
               ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                    WeatherStateImage(imagerUrl = imageUrl)
                    Text(
                        text = formatDecimals(data.list[0].temp.day) + "º",//Press and hold the ALT key and type 0176 with NumLock on
                        style = MaterialTheme.typography.h4, fontWeight = FontWeight.ExtraBold
                        )
                    Text(
                        text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic
                        )

                }

            }

        }

        HumidityWindPressureRow(weather = data.list[0])
        Divider()
        Spacer(modifier = Modifier.height(15.dp))
        SunsetSunRiseRow(weather = data.list[0])
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "This Week", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold
            )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
               ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
                      ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}


