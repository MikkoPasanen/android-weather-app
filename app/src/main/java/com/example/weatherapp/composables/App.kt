package com.example.weatherapp.composables

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.viewmodel.SettingsViewModel
import com.example.weatherapp.viewmodel.factories.SettingsViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.factories.WeatherViewModelFactory

/**
 * The main composable function that sets up the navigation and handles permissions.
 *
 * @param settingsViewModel The ViewModel instance for managing settings.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun App(settingsViewModel: SettingsViewModel) {
    val context = LocalContext.current

    val navController = rememberNavController()
    val weatherViewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(context)
    )

    // Launcher for location access request
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),

        // When user enters the decision on location access
        onResult = { permissions ->
            val locationPermissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            // Check if user has checked all location permissions to be able to continue
            val allLocationPermissionsGranted = locationPermissions.all { permissions[it] == true }

            // If user has granted location access, move to main view page
            if (allLocationPermissionsGranted) {
                navController.navigate("MainView") {
                    // Pop the navigation stack of all previous locations so we cannot
                    // navigate back to the no location error page
                    popUpTo(navController.graph.startDestinationId)
                }
            }
        }
    )

    // On initialization, ask the user for location access
    LaunchedEffect(Unit) {
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        ))

        // If the user has not granted location access, move to error page
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            navController.navigate("NoLocation")
        }
    }

    NavHost(navController = navController, startDestination = "MainView" ) {

        composable("MainView") {
            MainView(navController, weatherViewModel, settingsViewModel)
        }

        composable("NoLocation") {
            NoLocationScreen()
        }

        composable("DailyWeather/{tempUnit}/{darkMode}/{id}") { backStackEntry ->
            val tempUnit: String? = backStackEntry.arguments?.getString("tempUnit")
            val darkMode: Boolean = backStackEntry.arguments?.getString("darkMode").toBoolean()
            val id: Int? = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            DailyWeather(navController, weatherViewModel, id!!, darkMode, tempUnit!!)
        }

        composable("ApiError") {
            ApiErrorScreen(navController)
        }
    }
}