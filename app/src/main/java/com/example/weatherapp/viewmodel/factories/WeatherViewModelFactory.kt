package com.example.weatherapp.viewmodel.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.viewmodel.WeatherViewModel

/**
 * Factory class for creating instances of WeatherViewModel.
 *
 * @param context The application context.
 */
class WeatherViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return A new instance of the ViewModel.
     * @throws IllegalArgumentException if the ViewModel class is unknown.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
