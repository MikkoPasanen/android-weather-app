package com.example.weatherapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.model.DailyWeather
import com.example.weatherapp.model.LocationData
import com.example.weatherapp.util.displayDate
import com.example.weatherapp.util.displayTemperature
import com.example.weatherapp.util.getWeatherIcon
import kotlin.math.roundToInt

/**
 * Composable function for displaying the weather forecast for the next 7 days.
 *
 * @param dailyWeather The daily weather data for the next 7 days.
 * @param navController The navigation controller for navigating to detailed daily weather views.
 * @param tempUnit The unit for temperature measurement.
 * @param darkMode Boolean indicating whether dark mode is enabled.
 */
@Composable
fun Next7Days(
    dailyWeather: DailyWeather,
    navController: NavController,
    tempUnit: String,
    darkMode: Boolean
) {
    Text(
        "Next 7 days",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 10.dp)
    )
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(440.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            repeat(7) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("DailyWeather/${tempUnit}/${darkMode}/${it}")
                        }
                ) {
                    Text(
                        displayDate(dailyWeather.date[it]),
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = getWeatherIcon(dailyWeather.weatherCode[it])),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).weight(1f)
                    )
                    Text(
                        "${displayTemperature(dailyWeather.minTemperature[it], tempUnit).roundToInt()}° / ${displayTemperature(dailyWeather.maxTemperature[it], tempUnit).roundToInt()}°",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}