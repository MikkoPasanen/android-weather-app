package com.example.weatherapp.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formats the given timestamp (yyyy-MM-dd'T'HH:mm)
 * into a more readable HH:mm format.
 *
 * @param timeStamp The timestamp to be formatted.
 * @return The formatted timestamp in HH:mm format.
 */
fun formatHour(timeStamp: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateTime = LocalDateTime.parse(timeStamp)

    return dateTime.format(formatter)
}

/**
 * Formats the given date (yyyy-MM-dd)
 * into (yyyy-MM-dd'T'HH:mm) with the time being midnight 00:00.
 *
 * @param date The date to be formatted.
 * @return The formatted date in yyyy-MM-dd'T'HH:mm format.
 */
fun formatDate(date: String): String {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    val localDate = LocalDate.parse(date, dateFormatter)
    val startOfDay = localDate.atStartOfDay()

    return startOfDay.format(dateTimeFormatter)
}

/**
 * Formats the given date (yyyy-MM-dd)
 * into a more readable format (M/d EE).
 *
 * @param timeStamp The date to be formatted.
 * @return The formatted date in M/d EE format.
 */
fun displayDate(timeStamp: String): String {
    val formatter = DateTimeFormatter.ofPattern("M/d EE", Locale.ENGLISH)
    val dateTime = LocalDate.parse(timeStamp)

    return dateTime.format(formatter)
}
