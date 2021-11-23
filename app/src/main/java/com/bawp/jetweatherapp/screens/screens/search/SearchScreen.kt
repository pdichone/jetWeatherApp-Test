package com.bawp.jetweatherapp.screens.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bawp.jetweatherapp.components.CommonTextField
import com.bawp.jetweatherapp.components.InputField
import com.bawp.jetweatherapp.components.WeatherAppBar
import com.bawp.jetweatherapp.navigation.WeatherScreens

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController: NavController) {

    var mCity by remember { mutableStateOf("") }

    Scaffold(topBar = {
        WeatherAppBar(
            title = WeatherScreens.SearchScreen.name,
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController,
            //onAddActionClicked = {navController.navigate(WeatherScreens.SearchScreen.name)}
                     ){
            //Todo: navigate back and add push the city name to mainScreen
            navController.popBackStack()
        }
    }) {
        Surface {
            Column(verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally) {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).align(Alignment.CenterHorizontally)){ searchQuery ->
                    mCity = searchQuery
                    navController.navigate(WeatherScreens.MainScreen.name + "/$mCity")
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}) {
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Spokane",
                       onAction = KeyboardActions {
                           if (!valid) return@KeyboardActions
                           onSearch(searchQueryState.value.trim())
                           searchQueryState.value = ""
                           keyboardController?.hide()
                       })
    }
}
