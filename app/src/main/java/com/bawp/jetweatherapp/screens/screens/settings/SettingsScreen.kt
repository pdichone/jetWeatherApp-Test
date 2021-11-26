package com.bawp.jetweatherapp.screens.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.jetweatherapp.components.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController,
                   settingsViewModel: SettingsViewModel = hiltViewModel()) {

    var unitToggleState by remember {
        mutableStateOf(false)
    }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    var choiceDef by remember {
        mutableStateOf(0)
    }
    val defaultChoice =
        if (choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    if (!choiceFromDb.isNullOrEmpty()) {
        choiceDef = if (choiceFromDb[0].unit == "Imperial (F)"){
            0
        }else {
            1
        }

    }

    val answerState = remember(measurementUnits) {
        mutableStateOf<Int?>(choiceDef)//default imperial
    }
    val choiceState = remember() {
        mutableStateOf(defaultChoice) //default imperial
    }
    val selectedUnit: (Int) -> Unit = remember(measurementUnits) {
        {
            answerState.value = it
            choiceState.value = measurementUnits[answerState.value!!]
        }
    }

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController,
                     ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
               ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                Text(
                    "Change Units of Measurement", modifier = Modifier.padding(bottom = 15.dp)
                    )

                            IconToggleButton(
                                checked = !unitToggleState,
                                onCheckedChange = {
                                    unitToggleState = !it
                                    if (unitToggleState) {choiceState.value = "Imperial (F)"}else {choiceState.value = "Metric (C)"}
                                    Log.d("TAG", "MainContent: $unitToggleState")
                                }, modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .clip(shape = RectangleShape)
                                    .padding(5.dp)
                                    .background(Color.Magenta.copy(alpha = 0.4f))

                                            ) {

                                Text(text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC")

//               if (choiceFromDb.isNotEmpty()) {
//                   var text = ""
//                   val item = choiceFromDb[0].unit
//                   text = if (item == "Imperial (F)") {
//                       "Fahrenheit ºF"
//                   }else {
//                       "Celsius ºC"
//                   }
//
//                   Text(text = if (unitToggleState) text else if(text == "Fahrenheit ºF") "Celsius ºC" else )
//               }else {
//
//               }

            }

//                measurementUnits.forEachIndexed { index, choice ->
//                    Row(
//                        modifier = Modifier.padding(3.dp).fillMaxWidth().height(55.dp).border(
//                                width = 4.dp,
//                                brush = Brush.linearGradient(
//                                    colors = listOf(
//                                        Color(0xFFcdedee), Color(0xFF88AFB0)
//                                                   )
//                                                            ),
//                                shape = RoundedCornerShape(15.dp)
//                                                                                             ).clip(
//                                RoundedCornerShape(
//                                    topStartPercent = 50,
//                                    topEndPercent = 50,
//                                    bottomEndPercent = 50,
//                                    bottomStartPercent = 50
//                                                  )
//                                                                                                   )
//                            .background(Color.Transparent),
//                        verticalAlignment = Alignment.CenterVertically
//                       ) {
//
//                        RadioButton(selected = (answerState.value == index), onClick = {
//                            Log.d("Selected", "SettingsScreen: ${answerState.value}")
//                            selectedUnit(index)
//                        }, modifier = Modifier.padding(start = 16.dp)) //end rb
//                        Text(text = choice)
//                    }
//
//                }

                //Text(text = choiceState.value)

                Button(onClick = {
                    settingsViewModel.deleteAllUnits() //hack: delete all first
                    settingsViewModel.insertUnit(com.bawp.jetweatherapp.models.Unit(unit = choiceState.value))
                    //save/update selection to db!
//                   if ( choiceFromDb.isNullOrEmpty()){
//                       //insert
//                       settingsViewModel.insertUnit(com.bawp.jetweatherapp.models.Unit(unit = choiceState.value))
//                   }else {
//                       settingsViewModel.deleteAllUnits().run {
//                           settingsViewModel.insertUnit(com.bawp.jetweatherapp.models.Unit(unit = choiceState.value))
//                       }//delete all first
//                       //update
//                   }
                },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                                                        ),
                    content = {
                        Text(
                            text = "Save",
                            modifier = Modifier.padding(4.dp),
                            color = Color.White,
                            fontSize = 17.sp
                            )
                    })
            }
        }
    }
}
