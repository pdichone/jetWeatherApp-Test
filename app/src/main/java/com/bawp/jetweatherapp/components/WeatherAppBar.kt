package com.bawp.jetweatherapp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bawp.jetweatherapp.navigation.WeatherScreens
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*

@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
                 ) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {

        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(title = {

        Row(
            horizontalArrangement = Arrangement.Center
           ) {
            if (showProfile) {
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f)
                        .clickable { Log.d("Title", "WeatherAppBar: $title") })

            }
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = "menu",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable { onButtonClicked.invoke() })
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.35f))
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                )
        }
    }, actions = {

        Row(horizontalArrangement = Arrangement.Center) {

            if (showProfile) Row(horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {

                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")

                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "add")

                }


            } else Box {}

        }
    }, backgroundColor = Color.Transparent, elevation = 0.dp)
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("Add City", "Favorites")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
          ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(
                    Color.White
                           )
                    ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Row(Modifier) {
                        Icon(imageVector = if(s == "Add City") Icons.Default.AddCircle else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.LightGray)
                        Text(text = s, modifier = Modifier.clickable {
                            navController.navigate(WeatherScreens.SearchScreen.name)
                            Log.d("TAG", "ShowSettingDropDownMenu: $s")
                        }, fontWeight = FontWeight.W300)
                    }
                    if (index < 3) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }


    }
}