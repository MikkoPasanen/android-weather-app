package com.example.weatherapp.repository

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/**
 * Repository class responsible for handling device location requests.
 *
 * @property context The application context used to access location services.
 */
class LocationRepository(private val context: Context) {

    /**
     * Requests the current location of the device.
     *
     * @param callback A callback function that receives the location or null if the location could not be obtained.
     */
    fun requestLocation(callback: (Location?) -> Unit) {
        // Check if the user has granted location access
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Location has been granted, proceed with getting the location,
            // request the location only 1 time as we don't need to update the location
            // frequently
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            val locationRequest = LocationRequest.Builder(1000L)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMaxUpdates(1)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    callback(locationResult.lastLocation)
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            Log.d("Location", "Not granted!")
        }
    }
}
