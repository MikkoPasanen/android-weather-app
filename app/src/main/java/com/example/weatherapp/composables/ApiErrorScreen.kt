package com.example.weatherapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * Composable function that displays an error page when data fetching fails.
 *
 * @param navController The NavController used to navigate between composable screens.
 */
@Composable
fun ApiErrorScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("An error occurred while fetching data", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        Text("Check your internet connection and try again", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        TextButton(
            onClick = {
                navController.navigate("MainView")
            },
        ) {
            Text("Try again", fontSize = 25.sp, modifier = Modifier.padding(top = 30.dp))
        }

    }
}