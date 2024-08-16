package com.example.weatherapp.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.LocationData
import com.example.weatherapp.util.CacheManager
import com.example.weatherapp.util.processHourlyList
import com.example.weatherapp.viewmodel.LocationViewModel
import com.example.weatherapp.viewmodel.SettingsViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel


/**
 * Composable function representing the main view of the weather app.
 *
 * @param navController The navigation controller used for navigation.
 * @param weatherViewModel The view model for weather data.
 * @param settingsViewModel The view model for app settings.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    settingsViewModel: SettingsViewModel
) {
    val scrollState = rememberScrollState()

    // ViewModels
    val locationViewModel: LocationViewModel = viewModel()

    // Dialogs
    var showSearchDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    // Data loading state
    var isLoading by remember { mutableStateOf(false) }

    // On initialization / when location state changes
    LaunchedEffect(weatherViewModel.deviceLocation.value) {
        isLoading = true

        // On initialization / re-render, try to fetch data from viewmodel
        weatherViewModel.fetchWeatherData(locViewModel = locationViewModel) {

            // If we have errors with API, dispaly error page
            if (weatherViewModel.error.value) {
                navController.navigate("ApiError")
            } else {
                // Else stop loading and display data
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather App") },
                actions = {
                    IconButton(onClick = { showSearchDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            // If we are fetching data right now, display loading page
            if (isLoading) {
                Text(
                    text ="Fetching weather data...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 250.dp)
                )
                CircularProgressIndicator(modifier = Modifier
                    .padding(top = 30.dp)
                    .size(40.dp))
            } else {
                weatherViewModel.currentWeather.value?.let {
                    CurrentWeather(
                        it,
                        weatherViewModel.cityName.value,
                        settingsViewModel.tempUnit.value,
                        settingsViewModel.measurementType.value,
                        settingsViewModel.colorMode.value == "Darkmode",
                    )
                }
                weatherViewModel.hourlyWeather.value?.let {
                    Next24Hours(
                        processHourlyList(it, weatherViewModel.currentWeather.value!!.time),
                        settingsViewModel.tempUnit.value
                    )
                }
                weatherViewModel.dailyWeather.value?.let {
                    Next7Days(
                        it,
                        navController,
                        settingsViewModel.tempUnit.value,
                        settingsViewModel.colorMode.value == "Darkmode"
                    )
                }

                // Display search dialog pop-up
                if (showSearchDialog) {
                    SearchDialog(
                        onDismiss = { showSearchDialog = false },
                        onCitySelect = { loc, city ->
                            weatherViewModel.fetchWeatherData(
                                lat = loc.latitude,
                                lon = loc.longitude,
                                cityName = city,
                                locViewModel = locationViewModel
                            ) {
                                isLoading = false
                            }
                        }
                    )
                }

                // Display settings dialog pop-up
                if (showSettingsDialog) {
                    SettingsDialog(settingsViewModel) {
                        showSettingsDialog = false
                    }
                }
            }
        }
    }
}
