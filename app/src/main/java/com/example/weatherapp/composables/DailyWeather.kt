package com.example.weatherapp.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.model.DailyWeather
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.util.CacheManager
import com.example.weatherapp.util.displayDate
import com.example.weatherapp.util.displayTemperature
import com.example.weatherapp.util.formatDate
import com.example.weatherapp.util.formatHour
import com.example.weatherapp.util.getWeatherDescription
import com.example.weatherapp.util.getWeatherIcon
import com.example.weatherapp.util.processHourlyList
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

/**
 * Composable function that displays extra weather info page for a specific day.
 *
 * @param navController The navigation controller used for navigation.
 * @param weatherViewModel The view model for weather data.
 * @param index The index of the day in the forecast.
 * @param darkMode Boolean indicating if dark mode is enabled.
 * @param tempUnit The unit of temperature to display.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DailyWeather(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    index: Int,
    darkMode: Boolean,
    tempUnit: String
) {

    var showChartDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showChartDialog = true }) {
                        Image(
                            painter = painterResource(R.drawable.chart),
                            contentDescription = "Chart",
                            colorFilter = ColorFilter.tint(if(darkMode) Color.White else Color.Black)

                        )
                    }
                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 60.dp)
        ) {
            weatherViewModel.dailyWeather.value?.let {
                Text(
                    displayDate(it.date[index]),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = getWeatherIcon(it.weatherCode[index])),
                    contentDescription = null,
                    modifier = Modifier.size(170.dp)
                )
                Text(
                    "${displayTemperature(it.minTemperature[index], tempUnit).roundToInt()}° / ${displayTemperature(it.maxTemperature[index], tempUnit).roundToInt()}°",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(getWeatherDescription(it.weatherCode[index]), fontSize = 25.sp)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                        .height(130.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            InfoBox(
                                iconId = R.drawable.uv_foreground,
                                label = "UV-index",
                                value = it.uv[index].toDouble().roundToInt().toString(),
                                modifier = Modifier.weight(1f),
                                darkMode = darkMode
                            )
                            InfoBox(
                                iconId = R.drawable.rain_foreground,
                                label = "Rain chance",
                                value = "${it.rainChance[index]}%",
                                modifier = Modifier.weight(1f),
                                darkMode = darkMode
                            )
                        }
                        Divider(
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            InfoBox(
                                iconId = R.drawable.sunrise_foreground,
                                label = "Sunrise",
                                value = formatHour(it.sunrise[index]),
                                modifier = Modifier.weight(1f),
                                darkMode = darkMode
                            )
                            InfoBox(
                                iconId = R.drawable.sunset_foreground,
                                label = "Sunset",
                                value = formatHour(it.sunset[index]),
                                modifier = Modifier.weight(1f),
                                darkMode = darkMode
                            )
                        }
                    }
                }
            }
            weatherViewModel.hourlyWeather.value?.let {
                Next24Hours(
                    processHourlyList(it, formatDate(weatherViewModel.dailyWeather.value!!.date[index])),
                    tempUnit
                )
            }
        }

        if (showChartDialog) {
            ChartDialog(
                processHourlyList(
                    weatherViewModel.hourlyWeather.value!!,
                    formatDate(weatherViewModel.dailyWeather.value!!.date[index])
                    ),
                darkMode
            ) {
                showChartDialog = false
            }
        }
    }
}

@Composable
fun InfoBox(iconId: Int, label: String, value: String, modifier: Modifier, darkMode: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(if(darkMode) Color.White else Color.Black)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(label, style = TextStyle(fontSize = 16.sp))
            Text(value, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        }
    }
}

