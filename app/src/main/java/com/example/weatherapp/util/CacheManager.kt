package com.example.weatherapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weatherapp.model.DailyWeather
import com.example.weatherapp.model.WeatherData
import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import kotlin.math.exp

/**
 * A utility class for managing caching of weather data and app settings.
 *
 * @property context The application context.
 */
class CacheManager(private val context: Context) {

    /**
     * The shared preferences instance for storing cached data.
     */
    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences("AppCache", Context.MODE_PRIVATE)
    }

    /**
     * Retrieves and returns cached weather data from memory.
     *
     * @param cacheKey The key associated with the cached weather data.
     * @return The cached weather data, or null if no data is found.
     */
    fun getCachedWeatherData(cacheKey: String): WeatherData? {
        val cachedString = sp.getString(cacheKey, null)
        return if (cachedString != null) {
            Gson().fromJson(cachedString, WeatherData::class.java)
        } else {
            null
        }
    }

    /**
     * Stores weather data into memory.
     *
     * @param weatherData The weather data to be cached.
     * @param cacheKey The key associated with the cached data.
     */
    fun cacheWeatherData(weatherData: WeatherData?, cacheKey: String) {
        val editor = sp.edit()
        editor.putString(cacheKey, Gson().toJson(weatherData))
        editor.apply()
    }

    /**
     * Checks if the cached weather data is expired based on its timestamp.
     *
     * @param timeStamp The timestamp of the cached weather data.
     * @return True if the data is expired, false otherwise.
     */
    fun isWeatherCacheExpired(timeStamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val expirationTime = TimeUnit.MINUTES.toMillis(15)
        return currentTime - timeStamp > expirationTime
    }

    /**
     * Caches daily forecast data.
     *
     * @param dailyWeather The daily weather forecast data to be cached.
     */    fun cacheDailyForecast(dailyWeather: DailyWeather?) {
        val editor = sp.edit()
        editor.putString("daily_forecast", Gson().toJson(dailyWeather))
        editor.apply()
    }

    /**
     * Retrieves specific daily forecast data to show in notifications.
     *
     * @return The cached daily weather forecast data, or null if no data is found.
     */
    fun getSpecificDailyForecast(): DailyWeather? {
        val dailyWeatherString = sp.getString("daily_forecast", null)
        Log.d("Alarm receiver", dailyWeatherString ?: "null cache string")
        return if (dailyWeatherString != null) {
            Gson().fromJson(dailyWeatherString, DailyWeather::class.java)
        } else {
            null
        }
    }

    /**
     * Caches the selected temperature unit.
     *
     * @param tempUnit The selected temperature unit to be cached.
     */
    fun cacheTempUnit(tempUnit: String) {
        val editor = sp.edit()
        editor.putString("temp_unit", tempUnit)
        editor.apply()
    }

    /**
     * Retrieves the cached temperature unit.
     *
     * @return The cached temperature unit, defaulting to Celsius(°C) if no unit is found.
     */
    fun getCachedTempUnit(): String? {
        return sp.getString("temp_unit", "Celsius(°C)")
    }

    /**
     * Caches the selected measurement type (metric or imperial).
     *
     * @param measurementType The selected measurement type to be cached.
     */
    fun cacheMeasurementType(measurementType: String) {
        val editor = sp.edit()
        editor.putString("measurement_type", measurementType)
        editor.apply()
    }

    /**
     * Retrieves the cached measurement type.
     *
     * @return The cached measurement type, defaulting to Metric if no type is found.
     */
    fun getCachedMeasurementType(): String? {
        return sp.getString("measurement_type", "Metric")
    }

    /**
     * Caches the selected color mode (Darkmode or Lightmode).
     *
     * @param colorMode The selected color mode to be cached.
     */
    fun cacheColorMode(colorMode: String) {
        val editor = sp.edit()
        editor.putString("color_mode", colorMode)
        editor.apply()
    }

    /**
     * Retrieves the cached color mode.
     *
     * @return The cached color mode, defaulting to Darkmode if no mode is found.
     */
    fun getCachedColorMode(): String? {
        return sp.getString("color_mode", "Darkmode")
    }
}