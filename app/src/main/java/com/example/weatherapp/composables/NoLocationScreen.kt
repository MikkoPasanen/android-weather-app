package com.example.weatherapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R

/**
 * Composable function for displaying the error screen when GPS access is not granted or was denied.
 */
@Composable
fun NoLocationScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GPS access is not granted", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 200.dp))
        Text("or was denied", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Image(
            painter = painterResource(id = R.drawable.no_location),
            contentDescription = null,
            modifier = Modifier.width(150.dp).fillMaxSize().padding(bottom = 250.dp)
        )

    }
}