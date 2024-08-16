package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response from the Open Meteo API.
 *
 * @property current Contains the current weather data.
 * @property hourly Contains the hourly weather forecast data for 7 days.
 * @property daily Contains the daily weather forecast data for 7 days.
 */
data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyWeather,
    val daily: DailyWeather
)

/**
 * Data class used to create objects with relevant weather data to be stored in cache.
 *
 * @property current The current weather data.
 * @property hourly The hourly weather forecast data for 7 days.
 * @property daily The daily weather forecast data for 7 days.
 * @property timestamp The time when the object was put into cache, in milliseconds.
 * @property cityName The possible name of the city which is stored in the cache.
 */
data class WeatherData(
    val current: CurrentWeather?,
    val hourly: HourlyWeather?,
    val daily: DailyWeather?,
    val timestamp: Long,
    var cityName: String?
)

/**
 * Data class representing the current weather data.
 *
 * @property time The time of the weather data observation.
 * @property temperature The temperature at 2 meters above ground level in degrees Celsius.
 * @property humidity The relative humidity at 2 meters above ground level, as a percentage.
 * @property rain The amount of rain in millimeters.
 * @property weatherCode The weather condition code.
 * @property windSpeed The wind speed at 10 meters above ground level in meters per second.
 */
data class CurrentWeather(
    @SerializedName("time")
    val time: String,

    @SerializedName("temperature_2m")
    val temperature: Double,

    @SerializedName("relative_humidity_2m")
    val humidity: Int,

    @SerializedName("rain")
    val rain: Double,

    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("wind_speed_10m")
    val windSpeed: Double
)

/**
 * Data class representing the hourly weather forecast data for 7 days.
 *
 * @property time The list of times for the hourly forecast.
 * @property temperature The list of temperatures at 2 meters above ground level in degrees Celsius for each hour.
 * @property weatherCode The list of weather condition codes for each hour.
 */
data class HourlyWeather(
    @SerializedName("time")
    val time: MutableList<String>,

    @SerializedName("temperature_2m")
    val temperature: MutableList<Double>,

    @SerializedName("weather_code")
    val weatherCode: MutableList<Int>
)

/**
 * Data class representing the daily weather forecast data for 7 days.
 *
 * @property date The list of dates for the daily forecast.
 * @property weatherCode The list of weather condition codes for each day.
 * @property maxTemperature The list of maximum temperatures at 2 meters above ground level in degrees Celsius for each day.
 * @property minTemperature The list of minimum temperatures at 2 meters above ground level in degrees Celsius for each day.
 * @property sunrise The list of sunrise times for each day.
 * @property sunset The list of sunset times for each day.
 * @property uv The list of maximum UV index values for each day.
 * @property rainChance The list of maximum precipitation probability percentages for each day.
 */
data class DailyWeather(
    @SerializedName("time")
    val date: List<String>,

    @SerializedName("weather_code")
    val weatherCode: List<Int>,

    @SerializedName("temperature_2m_max")
    val maxTemperature: List<Double>,

    @SerializedName("temperature_2m_min")
    val minTemperature: List<Double>,

    @SerializedName("sunrise")
    val sunrise: List<String>,

    @SerializedName("sunset")
    val sunset: List<String>,

    @SerializedName("uv_index_max")
    val uv: List<String>,

    @SerializedName("precipitation_probability_max")
    val rainChance: List<Int>
)
