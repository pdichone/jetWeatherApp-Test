package com.bawp.jetweatherapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
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

@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onButtonClicked: () -> Unit = {}
                ) {

    TopAppBar(title = {
        Row(
           horizontalArrangement = Arrangement.Center){
            if (showProfile) {
                Icon(imageVector = Icons.Default.Menu,
                    contentDescription = "Logo Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f).clickable{

                        }
                    )

            }
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "arrow back",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable { onButtonClicked.invoke() })
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.35f))
            Text(text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                )


        }


    },
        actions = {
            IconButton(onClick = {

            }) {
                if (showProfile) Row() {
                    Icon(imageVector = Icons.Filled.Add ,
                        contentDescription = "Add" ,
                        // tint = Color.Green.copy(alpha = 0.4f)
                        )
                } else Box {}



            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp)

}