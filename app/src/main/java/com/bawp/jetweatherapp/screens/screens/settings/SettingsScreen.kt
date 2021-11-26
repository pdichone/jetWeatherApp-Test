package com.bawp.jetweatherapp.screens.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.jetweatherapp.components.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController,
                   settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultChoice =
        if (choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    if (choiceFromDb.isNotEmpty()) {
        Log.d("TAGG", "SettingsScreen: ${choiceFromDb.toString()}")
    }

    val answerState = remember(measurementUnits) {
        mutableStateOf<Int?>(0)//default imperial
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
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
               ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                Text(
                    "Change Units of Measurement", modifier = Modifier.padding(bottom = 15.dp)
                    )

                measurementUnits.forEachIndexed { index, choice ->
                    Row(
                        modifier = Modifier.padding(3.dp).fillMaxWidth().height(55.dp).border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFcdedee), Color(0xFF88AFB0)
                                                   )
                                                            ),
                                shape = RoundedCornerShape(15.dp)
                                                                                             ).clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                                  )
                                                                                                   )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                       ) {
                        RadioButton(selected = (answerState.value == index), onClick = {
                            selectedUnit(index)
                        }, modifier = Modifier.padding(start = 16.dp)) //end rb
                        Text(text = choice)
                    }

                }

                Text(text = choiceState.value)
                Log.d("TAG", "SettingsScreen: ${choiceFromDb.toList()}")
                Button(onClick = {
                    settingsViewModel.deleteAllUnits()
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
                    modifier = Modifier.padding(3.dp)
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
