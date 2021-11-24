package com.bawp.jetweatherapp.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bawp.jetweatherapp.R
import com.bawp.jetweatherapp.models.Favorite
import com.bawp.jetweatherapp.screens.screens.favorites.FavoriteViewModel

@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
                 ) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
//
    }
    val context = LocalContext.current

    if (showDialog.value) {

        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(title = {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
           ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
                )
        }
    }, modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
        actions = {

        Row(horizontalArrangement = Arrangement.Center) {

            if (isMainScreen) Row(horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {

                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")

                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "more")
                }
            } else Box {}
        }
    },
        backgroundColor = Color.Transparent, elevation = elevation,
             navigationIcon = {

                 if (icon != null) {
                     Icon(imageVector = icon,
                         contentDescription = null,
                         tint = MaterialTheme.colors.onSecondary,
                         modifier = Modifier.clickable { onButtonClicked.invoke() })
                 }
                 if (isMainScreen) {
                     //Check to see if this city is already a favorite, if so, don't show the fav icon
                     val isAlreadyFav = viewModel.favList.collectAsState().value.filter { item ->
                         (item.city == title.split(",")[0])

                     }
                     if (isAlreadyFav.isNullOrEmpty())
                         Icon(imageVector = Icons.Default.Favorite,
                             contentDescription = "Favorite Icon",
                             modifier = Modifier
                                 .clip(RoundedCornerShape(12.dp))
                                 .scale(0.9f)
                                 .clickable {
                                     viewModel
                                         .insertFavorite(
                                             Favorite(
                                                 city = title.split(",")[0],
                                                 country = title.split(",")[1]
                                                     )
                                                        )
                                         .run {

                                             showIt.value = true
                                         }

                                 }, tint = Color.Red.copy(alpha = 0.6f)) else {
                         showIt.value = false
                         Box() {} }
                     ShowToast(context = context, showIt)
                 }

                // Icon(imageVector = Icons.Default.Delete, contentDescription = null)

             })
}

@Composable
private fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) { Toast.makeText(
        context, stringResource(id = R.string.item_added), Toast.LENGTH_SHORT
                  ).show() }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf( "Favorites", "About", "Settings")
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
                .background(Color.White)
                    ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Row(Modifier) {
                        Icon(imageVector = when (s) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        },
                            contentDescription = null,
                            tint = Color.LightGray)
                        Text(text = s, modifier = Modifier.clickable {
                            navController.navigate(
                                when (s) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                                                  )
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