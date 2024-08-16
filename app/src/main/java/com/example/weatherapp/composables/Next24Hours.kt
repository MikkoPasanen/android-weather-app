package com.example.weatherapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.util.displayTemperature
import com.example.weatherapp.util.getWeatherIcon
import kotlin.math.roundToInt

/**
 * Composable function for displaying the weather forecast for the next 24 hours.
 *
 * @param hourlyWeather The hourly weather data for the next 24 hours.
 * @param tempUnit The unit for temperature measurement.
 */
@Composable
fun Next24Hours(hourlyWeather: HourlyWeather, tempUnit: String) {

    Text("Next 24h", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
    LazyRow {
        items(24) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .height(110.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        hourlyWeather.time[it],
                        Modifier.padding(top= 7.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = getWeatherIcon(hourlyWeather.weatherCode[it])),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "${displayTemperature(hourlyWeather.temperature[it], tempUnit).roundToInt()}°",
                        Modifier.padding(top= 7.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}