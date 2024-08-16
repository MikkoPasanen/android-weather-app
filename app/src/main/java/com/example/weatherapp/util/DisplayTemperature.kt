package com.example.weatherapp.util

/**
 * Converts the given temperature to the specified temperature unit.
 *
 * @param temp The temperature to be converted.
 * @param unit The target temperature unit ("Celsius(°C)" or "Fahrenheit(°F)").
 * @return The temperature converted to the specified unit.
 */
fun displayTemperature(temp: Double, unit: String): Double {
    return if (unit == "Fahrenheit(°F)") (temp * 1.8) + 32 else temp
}