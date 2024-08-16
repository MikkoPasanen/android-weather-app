package com.example.weatherapp.viewmodel

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp.repository.LocationRepository

/**
 * ViewModel for handling device location-related operations.
 */
class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationRepository = LocationRepository(application)

    /**
     * Requests the current device location.
     *
     * @param cb Callback function to handle the retrieved location.
     */
    fun getLocation(cb: (location: Location?) -> Unit) {
        locationRepository.requestLocation { location ->
            cb(location)
        }
    }
}