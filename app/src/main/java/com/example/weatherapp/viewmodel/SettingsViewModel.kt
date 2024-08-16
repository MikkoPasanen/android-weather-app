package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherapp.util.CacheManager

/**
 * ViewModel for managing settings related to the weather application.
 *
 * @param context The application context.
 */
class SettingsViewModel(context: Context): ViewModel() {

    private val cacheManager = CacheManager(context)

    // Temp unit
    private var _tempUnit = mutableStateOf(cacheManager.getCachedTempUnit() ?: "Celsius(°C)")
    val tempUnit: State<String> get() = _tempUnit

    // Measurement type
    private var _measurementType = mutableStateOf(cacheManager.getCachedMeasurementType() ?: "Metric")
    val measurementType: State<String> get() = _measurementType

    // Color mode
    private var _colorMode = mutableStateOf(cacheManager.getCachedColorMode() ?: "Darkmode")
    val colorMode: State<String> get() = _colorMode

    /**
     * Sets the temperature unit.
     *
     * @param newTemp The new temperature unit.
     */
    fun setTempUnit(newTemp: String) {
        _tempUnit.value = newTemp
        cacheManager.cacheTempUnit(newTemp)
    }

    /**
     * Sets the measurement type.
     *
     * @param newType The new measurement type.
     */
    fun setMeasurementType(newType: String) {
        _measurementType.value = newType
        cacheManager.cacheMeasurementType(newType)
    }

    /**
     * Sets the color mode.
     *
     * @param newColorMode The new color mode.
     */
    fun setColorMode(newColorMode: String) {
        _colorMode.value = newColorMode
        cacheManager.cacheColorMode(newColorMode)
    }
}