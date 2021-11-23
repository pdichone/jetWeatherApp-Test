package com.bawp.jetweatherapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bawp.jetweatherapp.R
import com.bawp.jetweatherapp.model.WeatherItem
import com.bawp.jetweatherapp.utils.formatDate
import com.bawp.jetweatherapp.utils.formatDateTime
import com.bawp.jetweatherapp.utils.formatDecimals


@Composable
fun SunsetSunRiseRow(weather: WeatherItem?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp)
       ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
           ) {

            Row {
                Image(
                    painter = painterResource(id = R.drawable.sunrise),
                    contentDescription = "sunrise",
                    Modifier.size(25.dp)
                     )
                Text(text = formatDateTime(weather!!.sunrise))
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.14f))
            Row(/*modifier = Modifier.offset(y = 40.dp),*/
                horizontalArrangement = Arrangement.End
               ) {
                Text(text = formatDateTime(weather!!.sunset))
                Image(
                    painter = painterResource(id = R.drawable.sunset),
                    contentDescription = "sunset",
                    Modifier.size(25.dp)
                     )
            }
        }
    }
}

@Preview
@Composable
fun HumidityWindPressureRow(weather: WeatherItem? = null) {
    Row(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween


       ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
                )
            Text(text = "${weather?.humidity.toString()}%")
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        Row(modifier = Modifier.padding(3.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp)
                )
            Text(text = "${weather?.pressure.toString()} psi")
        }
        Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        Row(modifier = Modifier.padding(3.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp)
                )
            Text(text = " %.0f".format(weather?.speed) + "mph")
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
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
        Column(Modifier.padding(12.dp)) {
            Text(text = "Today")

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
               ) {
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
//                    WeatherDetailRow("Minimum Temperature: ", weather.temp.min.toString())
//                    WeatherDetailRow("Maximum Temperature: ", weather.temp.max.toString())
//                    WeatherDetailRow("Pressure: ", weather.pressure.toString())
//                    WeatherDetailRow("Date: ", formatDate(weather.dt))
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

@ExperimentalCoilApi
@Preview
@Composable
fun WeatherDetailRow(weather: WeatherItem? = null) {

    val imageUrl = "https://openweathermap.org/img/wn/${weather!!.weather[0].icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
           ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
           ) {
            Text(text = formatDate(weather.dt).split(",")[0], modifier = Modifier.padding(start = 4.dp))
            WeatherStateImage(imagerUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp), shape = CircleShape, color = Color(0xFFFFC400)
                   ) {
                Text(
                    weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                    )
            }
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold
                                     )
                         ) {
                    append(formatDecimals(weather.temp.max) + "º")
                }
                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(formatDecimals(weather.temp.min) + "º")
                }
            }, modifier = Modifier.padding(end = 2.dp))
        }


    }
}