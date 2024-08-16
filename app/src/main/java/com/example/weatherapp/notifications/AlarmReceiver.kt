package com.example.weatherapp.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.weatherapp.model.DailyWeather
import com.example.weatherapp.util.CacheManager
import com.example.weatherapp.util.displayTemperature
import com.example.weatherapp.util.getWeatherDescription
import com.example.weatherapp.util.getWeatherIcon
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.math.roundToInt

/**
 * BroadcastReceiver that handles alarm events and triggers weather notifications.
 */
class AlarmReceiver : BroadcastReceiver() {

    /**
     * Called when the BroadcastReceiver receives an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("alarm receiver", "alarm received")
        context?.let { cntx ->
            val notificationManager = cntx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifier = Notifier(notificationManager, cntx)
            val cacheManager = CacheManager(cntx)

            // Get necessary info from cache
            val dailyWeather: DailyWeather? = cacheManager.getSpecificDailyForecast()
            val cachedTempUnit: String? = cacheManager.getCachedTempUnit()
            val tempUnit = cachedTempUnit ?: "Celsius(°C)"

            // If we have data to display
            if (dailyWeather != null) {
                Log.d("Alarm receiver", "cache was not null")
                val index = dailyWeather.date.indexOf(currentDate())

                val title = "Weather today"
                val content = "${getWeatherDescription(dailyWeather.weatherCode[index])}\nMin: ${displayTemperature(dailyWeather.minTemperature[index], tempUnit).roundToInt()}° / Max: ${displayTemperature(dailyWeather.maxTemperature[index], tempUnit).roundToInt()}°"
                val icon = getWeatherIcon(dailyWeather.weatherCode[index])

                notifier.showNotification(title, content, icon)
            } else {
                Log.d("Alarm receiver", "cache was null")
            }
        }
    }

    /**
     * Returns the current date formatted as "yyyy-MM-dd".
     *
     * @return The current date in "yyyy-MM-dd" format.
     */
    private fun currentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }
}