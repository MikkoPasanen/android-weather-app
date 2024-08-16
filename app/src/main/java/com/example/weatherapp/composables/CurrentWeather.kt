package com.example.weatherapp.composables

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.util.displayTemperature
import com.example.weatherapp.util.displayWindSpeed
import com.example.weatherapp.util.formatHour
import com.example.weatherapp.util.getWeatherDescription
import com.example.weatherapp.util.getWeatherIcon
import kotlin.math.roundToInt

/**
 * Composable function that displays the current weather information in a card layout.
 *
 * @param currentWeather The current weather data.
 * @param cityName The name of the city.
 * @param tempUnit The unit of temperature to display.
 * @param measurementType The unit of measurement for wind speed.
 * @param darkMode Boolean indicating if dark mode is enabled.
 */
@Composable
fun CurrentWeather(
    currentWeather: CurrentWeather,
    cityName: String?,
    tempUnit: String,
    measurementType: String,
    darkMode: Boolean,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .height(460.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                cityName?.let {
                    Text(
                        cityName,
                        Modifier.padding(10.dp),
                        fontSize = 25.sp
                    )
                }
                Text(
                    "Today ${formatHour(currentWeather.time)}",
                    Modifier.padding(10.dp),
                    fontSize = 25.sp
                )
            }
            Image(
                painter = painterResource(id = getWeatherIcon(currentWeather.weatherCode)),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )
            Text(
                "${displayTemperature(currentWeather.temperature, tempUnit).roundToInt()}°",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                getWeatherDescription(currentWeather.weatherCode),
                fontSize = 25.sp,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Humidity")
                    Text(
                        "${currentWeather.humidity}%",
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.humidity_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(if(darkMode) Color.White else Color.Black)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Wind speed")
                    Text(
                        displayWindSpeed(currentWeather.windSpeed, measurementType),
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.wind_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(if(darkMode) Color.White else Color.Black)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Rain")
                    Text(
                        "${currentWeather.rain} mm",
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 20.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.rain_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        colorFilter = ColorFilter.tint(if(darkMode) Color.White else Color.Black)
                    )
                }
            }
        }
    }
}
