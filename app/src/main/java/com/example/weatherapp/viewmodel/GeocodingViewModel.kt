package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.GeocodingApi
import com.example.weatherapp.model.CityData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for handling geocoding related operations.
 */
class GeocodingViewModel: ViewModel() {

    /**
     * Fetches a list of cities based on the provided city name.
     *
     * @param cityName The name of the city to fetch data for.
     * @param cb Callback function to handle the result. It provides loading status, list of city data, and possible error.
     */
    fun fetchCities(
        cityName: String,
        cb: (loading: Boolean, List<CityData>, Throwable?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = GeocodingApi.service.getCityData(cityName)
                cb(false, response.cities, null)
            } catch (e: Exception) {
                cb(false, emptyList(), e)
            }
        }
    }
}