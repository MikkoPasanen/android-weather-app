package com.example.weatherapp.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.DailyWeather
import com.example.weatherapp.model.HourlyWeather
import com.example.weatherapp.model.LocationData
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.util.CacheManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for managing weather-related data.
 *
 * @param context The application context.
 */
class WeatherViewModel(context: Context): ViewModel() {

    // Cache manager
    private val cacheManager: CacheManager = CacheManager(context)

    // Current weather
    private var _currentWeather = mutableStateOf<CurrentWeather?>(null)
    val currentWeather: State<CurrentWeather?> get() = _currentWeather

    // Hourly weather
    private var _hourlyWeather = mutableStateOf<HourlyWeather?>(null)
    val hourlyWeather: State<HourlyWeather?> get() = _hourlyWeather

    // Daily weather
    private var _dailyWeather = mutableStateOf<DailyWeather?>(null)
    val dailyWeather: State<DailyWeather?> get() = _dailyWeather

    // City name
    private var _cityName = mutableStateOf<String?>(null)
    val cityName: State<String?> get() = _cityName

    // City coordinates if we fetch data with city names instead of device location
    private var _cityLocation = mutableStateOf<LocationData?>(null)

    private var _deviceLocation = mutableStateOf<Location?>(null)
    val deviceLocation: State<Location?> get() = _deviceLocation

    private var _error = mutableStateOf<Boolean>(false)
    val error: State<Boolean> get() = _error


    /**
     * Fetches weather data based on the provided coordinates or city name.
     *
     * @param lat Latitude of the location.
     * @param lon Longitude of the location.
     * @param cityName The name of the city.
     * @param locViewModel The LocationViewModel instance.
     * @param callback Callback function to be invoked after data fetching.
     */
    fun fetchWeatherData(
        lat: Double? = null,
        lon: Double? = null,
        cityName: String? = null,
        locViewModel: LocationViewModel,
        callback: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Initialize coords object
                val coords: LocationData?

                // Reset the error status to false
                _error.value = false

                // If device location has not been fetched yet,
                // fetch it
                if (deviceLocation.value == null) {
                    locViewModel.getLocation { _deviceLocation.value = it }
                }

                // If lat & lon parameters are null, use either city location coordinates
                // or devices location to fetch weather data
                if (lat == null && lon == null) {
                    coords = _cityLocation.value ?: _deviceLocation.value?.let {
                        LocationData(
                            it.latitude,
                            it.longitude
                        )
                    }
                } else {
                    coords = LocationData(lat!!, lon!!)
                    _cityLocation.value = coords
                }

                // If we have coordinates to work with
                coords?.let { loc ->
                    // Choose the cache key to use depending on if we are using devices location
                    // or city coordinates to fetch data
                    val cacheKey =
                        if (_cityLocation.value == null) "device" else "city"

                    // Get cached weather data with given cache key
                    val cachedData = cacheManager.getCachedWeatherData(cacheKey)

                    // If cached weather data is not null &
                    // is not older than 15 minutes &
                    // we are not fetching data for a new city so lat parameter is null,
                    // use the cached data instead of fetching new data
                    if (cachedData != null && !cacheManager.isWeatherCacheExpired(cachedData.timestamp) && lat == null) {
                        _currentWeather.value = cachedData.current
                        _hourlyWeather.value = cachedData.hourly
                        _dailyWeather.value = cachedData.daily
                        _cityName.value = cachedData.cityName
                        Log.d("Cache", "Found cached data with ${cacheKey}!")
                        callback()
                    } else {
                        Log.d("API", "Didn't find cached data!")

                        val response = WeatherApi.service.getWeatherData(loc.latitude, loc.longitude) // use either loc or parameters depending if theyre null or not

                        // Update UI in Main thread
                        withContext(Dispatchers.Main) {
                            _cityName.value = cityName

                            _currentWeather.value = CurrentWeather(
                                response.current.time,
                                response.current.temperature,
                                response.current.humidity,
                                response.current.rain,
                                response.current.weatherCode,
                                response.current.windSpeed
                            )

                            _hourlyWeather.value = HourlyWeather(
                                response.hourly.time,
                                response.hourly.temperature,
                                response.hourly.weatherCode
                            )

                            _dailyWeather.value = DailyWeather(
                                response.daily.date,
                                response.daily.weatherCode,
                                response.daily.maxTemperature,
                                response.daily.minTemperature,
                                response.daily.sunrise,
                                response.daily.sunset,
                                response.daily.uv,
                                response.daily.rainChance
                            )

                            // Cache the fetched weather data to reduce future API calls
                            cacheManager.cacheWeatherData(
                                WeatherData(
                                    _currentWeather.value,
                                    _hourlyWeather.value,
                                    _dailyWeather.value,
                                    System.currentTimeMillis(),
                                    _cityName.value
                                ),
                                cacheKey
                            )

                            // Cache daily weather forecast for notifications
                            // but only if we are using device location weather data
                            if (cacheKey == "device") {
                                cacheManager.cacheDailyForecast(_dailyWeather.value)
                            }

                            // Notify MainView that we are done with data fetch
                            callback()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("API error", e.message!!)

                    // In case of any errors, set the error value to true and notify MainView
                    _error.value = true
                    callback()
                }
            }

        }
    }
}