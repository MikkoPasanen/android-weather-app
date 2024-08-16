package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.composables.App
import com.example.weatherapp.model.NotificationItem
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.notifications.NotificationScheduler
import com.example.weatherapp.viewmodel.SettingsViewModel
import com.example.weatherapp.viewmodel.factories.SettingsViewModelFactory
import java.util.Calendar

/**
 * The main activity of the application.
 */
class MainActivity : ComponentActivity() {
    private val notificationScheduler by lazy {
        NotificationScheduler(this)
    }

    /**
     * Called when the activity is starting.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleNotification()

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(this)
            )
            WeatherAppTheme(darkTheme = settingsViewModel.colorMode.value == "Darkmode") {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(settingsViewModel)
                }
            }
        }
    }

    /**
     * Schedule notification for daily weather update.
     */
    private fun scheduleNotification() {
        val item = NotificationItem(
            time = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 20)
                set(Calendar.MINUTE, 14)
            }.timeInMillis,
            id = 1
        )

        notificationScheduler.schedule(item)
    }
}



