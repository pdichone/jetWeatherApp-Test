package com.bawp.jetweatherapp.screens.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bawp.jetweatherapp.components.WeatherAppBar
import com.bawp.jetweatherapp.model.WeatherItem
import com.bawp.jetweatherapp.utils.formatDate
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bawp.jetweatherapp.R
import com.bawp.jetweatherapp.utils.formatDateTime


@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(), navController: NavHostController
              ) {
    val data = viewModel.data.value.data

    //if (data == null) LinearProgressIndicator() else Text(text = data.city.name)

    Scaffold(topBar = {

        if (data != null){
            WeatherAppBar(
                title = data.city.name + ", ${data.city.country}", navController = navController
                         )
        }else {
            CircularProgressIndicator()
        }


    }, bottomBar = {},
            backgroundColor = Color.LightGray.copy(alpha = 0.08f)) {

        if (viewModel.data.value.data == null) {
            LinearProgressIndicator(color = MaterialTheme.colors.primaryVariant)

        } else {
            MainContent(viewModel)
        }

    }


    // Log.d("Main", "MainScreen: ${viewModel.data.value.data!!}")


}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
private fun MainContent(viewModel: MainViewModel) {
     val data = viewModel.data.value.data
    val imageUrl = "https://openweathermap.org/img/wn/${data!!.list[0].weather[0].icon}.png"

    val linearGradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF6F6B5).copy(alpha = 0.08f), Color(0xFFF4F4C7)
            .copy(alpha = 0.4f)),
        tileMode = TileMode.Repeated)
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

          ) {

            Text(
                text = formatDate(data.list[0].dt),//"Today Nov 29",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
                )
          Surface(modifier = Modifier
              .padding(4.dp)
              .size(210.dp),
              shape = CircleShape) {
              Box(
                  modifier = Modifier
                      .background(linearGradientBrush)
                      .padding(horizontal = 16.dp, vertical = 8.dp),
                  contentAlignment = Alignment.Center
                 ) {
                  Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                     WeatherStateImage(imagerUrl = imageUrl)
                      Text(text = " %.0f".format(data.list[0].temp.max) + "º",//Press and hold the ALT key and type 0176 with NumLock on
                          style = MaterialTheme.typography.h4,
                          fontWeight = FontWeight.ExtraBold)
                      Text(text = data.list[0].weather[0].main,
                          fontStyle = FontStyle.Italic)
                      
                  }

              }

          }

        HumidityWindPressureRow(weather = data.list[0])

        SunsetSunRiseRow(weather = data.list[0])


//        LazyColumn {
//            items(items = viewModel.data.value.data!!.list) { item ->
//                WeatherCard(weather = item)
//
//            }
//        }

    }
}


@Composable
fun SunsetSunRiseRow(weather: WeatherItem?) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)){
        Row {

            Row() {
                Image(painter = painterResource(id = R.drawable.sunrise),
                    contentDescription = "sunrise",
                    Modifier.size(25.dp))
                Text(text = formatDateTime(weather!!.sunrise))
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.54f))
            Row(modifier = Modifier.offset(y = 40.dp),
                horizontalArrangement = Arrangement.End) {
                Text(text = formatDateTime(weather!!.sunset))
                Image(painter = painterResource(id = R.drawable.sunset),
                    contentDescription = "sunset",
                    Modifier.size(25.dp))
            }
        }
    }
}

@Preview
@Composable
fun HumidityWindPressureRow(weather: WeatherItem? = null) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(0.97f),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather?.humidity.toString()}%")
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        Row(modifier = Modifier.padding(3.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather?.pressure.toString()} psi")
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        Row(modifier = Modifier.padding(3.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp))
            Text(text =  " %.0f".format(weather?.speed) + "mph")
        }

    }
}
@Composable
fun RoundedCard(weather: WeatherItem? = null) {
     Card(
         Modifier
             .padding(6.dp)
             .fillMaxWidth()
             .height(240.dp),
         elevation = 5.dp,
         shape = RoundedCornerShape(corner = CornerSize(12.dp)),
         backgroundColor = Color.LightGray,
         border = BorderStroke(width = 1.dp, color = Color.LightGray)) {
          Column(Modifier.padding(12.dp)) {
               Text(text = "Today")

              Row(
                  horizontalArrangement = Arrangement.SpaceBetween) {
                  Column() {
                      Text(text = "Pressure")
                      Text(text = "89mb")
                  }
                  Spacer(modifier = Modifier.width(80.dp))
                  Column() {
                      Text(text = "Visibility")
                      Text(text = "5 Km")
                  }
                  Spacer(modifier = Modifier.width(80.dp))
                  Column() {
                      Text(text = "Humidity")
                      Text(text = "94%")
                  }

              }



          }
     }

}
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun WeatherCard(
    weather: WeatherItem
               ) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    var expanded by remember { mutableStateOf(false) }
    var cardHeight by remember { mutableStateOf(300) }

    Card(
        border = BorderStroke(3.dp, Color.Red),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(10.dp)
        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
              ) {
            WeatherStateImage(imagerUrl = imageUrl)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = weather.weather[0].description.uppercase())
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
               ) {
                WeatherDataInfoRow(
                    image = Icons.Default.ThumbUp, description = weather.humidity.toString()
                                  )
                WeatherDataInfoRow(
                    image = Icons.Default.Create, description = weather.weather[0].description
                                  )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        expanded = !expanded
                        if (cardHeight == 300) cardHeight = 400 else cardHeight = 300
                    })
            Spacer(modifier = Modifier.height(10.dp))
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                      ) {
                    WeatherDetailRow("Minimum Temperature: ", weather.temp.min.toString())
                    WeatherDetailRow("Maximum Temperature: ", weather.temp.max.toString())
                    WeatherDetailRow("Pressure: ", weather.pressure.toString())
                    WeatherDetailRow("Date: ", formatDate(weather.dt))
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun WeatherStateImage(
    imagerUrl: String
                     ) {
    Image(
        painter = rememberImagePainter(imagerUrl),
        contentDescription = "Image",
        modifier = Modifier.size(80.dp)
         )
}

@Composable
fun WeatherDataInfoRow(
    image: ImageVector, description: String
                      ) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
          ) {
        Image(
            imageVector = image, contentDescription = "image", modifier = Modifier.size(50.dp)
             )
//        Image(
//            image
//            contentDescription = "image",
//            modifier = Modifier.size(50.dp)
//             )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = description)
    }
}

@Composable
fun WeatherDetailRow(
    text: String, description: String
                    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
       ) {
        Text(text = text)
        Text(text = description)
    }
}