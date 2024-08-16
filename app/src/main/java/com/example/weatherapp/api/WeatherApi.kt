package com.example.weatherapp.api

import com.example.weatherapp.model.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the Weather API.
 * This interface defines method to interact with the Open Meteo API
 * to fetch weather data using .
 */
interface WeatherApi {
    /**
     * Fetches weather data based on coordinates and the specified parameters.
     *
     * @param latitude The latitude of the location for which to fetch weather data.
     * @param longitude The longitude of the location for which to fetch weather data.
     * @param currentParams The parameters for current weather data, defaulting to temperature, humidity, rain, weather code, and wind speed.
     * @param hourlyParams The parameters for hourly weather data, defaulting to temperature and weather code.
     * @param dailyParams The parameters for daily weather data, defaulting to weather code, max/min temperature, sunrise, sunset, UV index, and precipitation probability.
     * @param windSpeedUnit The unit for wind speed, defaulting to meters per second.
     * @param timezone The timezone for the weather data, defaulting to auto.
     * @return A [WeatherResponse] object containing the weather data.
     */
    @GET("v1/forecast")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") currentParams: String = "temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m",
        @Query("hourly") hourlyParams: String = "temperature_2m,weather_code",
        @Query("daily") dailyParams: String = "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,precipitation_probability_max",
        @Query("wind_speed_unit") windSpeedUnit: String = "ms",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse

    companion object {
        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .build()
        }

        private const val BASE_URL = "https://api.open-meteo.com/"

        /**
         * The service instance of [WeatherApi].
         * This instance is lazily initialized using Retrofit.
         */
        val service: WeatherApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WeatherApi::class.java)
        }
    }
}

