package com.example.weatherapp.util

import com.example.weatherapp.model.HourlyWeather
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Process the hourly weather list to provide data for the next 24 hours from the given current time.
 *
 * @param hourlyWeather The hourly weather data.
 * @param currentTime The current time in the format "yyyy-MM-dd'T'HH:mm".
 * @return HourlyWeather object containing data for the next 24 hours from the given current time.
 */
fun processHourlyList(hourlyWeather: HourlyWeather, currentTime: String): HourlyWeather {

    // Format the given currentTime timestamp (yyyy-MM-dd'T'HH:mm) by setting the minutes into 0
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val dateTime = LocalDateTime.parse(currentTime, formatter)
    val modifiedDateTime = dateTime.withMinute(0)
    val modifiedCurrentTime = modifiedDateTime.format(formatter)

    // Get the index of the given currentTime in the hourlyWeather list
    val index = hourlyWeather.time.indexOf(modifiedCurrentTime)

    // Make a sublist starting from the index found above
    val timeList = hourlyWeather.time.subList(index, hourlyWeather.time.size).toMutableList()
    val weatherCodeList = hourlyWeather.weatherCode.subList(index, hourlyWeather.weatherCode.size).toMutableList()
    val temperatureList = hourlyWeather.temperature.subList(index, hourlyWeather.temperature.size).toMutableList()

    // Limit the list sizes to 24 items
    // leaving us with the next 24 hours starting from the given currentTime parameter
    if (timeList.size > 24) {
        timeList.subList(24, timeList.size).clear()
        weatherCodeList.subList(24, weatherCodeList.size).clear()
        temperatureList.subList(24, temperatureList.size).clear()
    }

    return HourlyWeather(
        timeList.map { formatHour(it) }.toMutableList(), // Call formatHour for each time item to format the time into HH:mm
        temperatureList,
        weatherCodeList
    )
}
