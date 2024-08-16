package com.example.weatherapp.util

/**
 * Formats the wind speed based on the given unit.
 *
 * @param speed The wind speed to be formatted.
 * @param unit The unit to use for formatting ("Imperial" or "Metric").
 * @return The formatted wind speed string.
 */
fun displayWindSpeed(speed: Double, unit: String): String {
    return if (unit == "Imperial") "${(speed * 2.2369 * 10).toInt() / 10.0} Mph" else "${(speed * 10).toInt() / 10.0} m/s"
}