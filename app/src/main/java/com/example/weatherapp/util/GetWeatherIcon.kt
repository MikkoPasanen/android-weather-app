package com.example.weatherapp.util

import com.example.weatherapp.R

/**
 * Returns an Int representing the weather icon based on the given weather code.
 *
 * @param code The weather code.
 * @return The weather resource ID.
 */fun getWeatherIcon(code: Int): Int {
    return when(code) {
        0 -> R.drawable.sunny
        1 -> R.drawable.sunnycloudy
        2 -> R.drawable.sunnycloudy
        3 -> R.drawable.cloudy
        45 -> R.drawable.verycloudy
        48 -> R.drawable.verycloudy
        51 -> R.drawable.rainshower
        53 -> R.drawable.rainshower
        55 -> R.drawable.rainshower
        56 -> R.drawable.snowyrainy
        57 -> R.drawable.snowyrainy
        61 -> R.drawable.rainy
        63 -> R.drawable.rainy
        65 -> R.drawable.rainy
        66 -> R.drawable.snowyrainy
        67 -> R.drawable.snowyrainy
        71 -> R.drawable.snowy
        73 -> R.drawable.heavysnow
        75 -> R.drawable.heavysnow
        77 -> R.drawable.heavysnow
        80 -> R.drawable.rainshower
        81 -> R.drawable.rainshower
        82 -> R.drawable.rainshower
        85 -> R.drawable.snowy
        86 -> R.drawable.snowy
        95 -> R.drawable.thunder
        96 -> R.drawable.rainythunder
        99 -> R.drawable.rainythunder
        else -> R.drawable.sunny

    }
}