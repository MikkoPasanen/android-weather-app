package com.example.weatherapp.api

import com.example.weatherapp.model.CityResponse
import com.example.weatherapp.model.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the Geocoding API.
 * This interface defines method to interact with the geocoding API
 * to fetch specific city weather data.
 */
interface GeocodingApi {
    /**
     * Fetches city data based on the city name.
     *
     * @param name The name of the city to search for.
     * @return A [CityResponse] object containing the city data.
     */
    @GET("v1/search")
    suspend fun getCityData(
        @Query("name") name: String,
    ): CityResponse

    companion object {
        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .build()
        }

        private const val BASE_URL = "https://geocoding-api.open-meteo.com/"

        /**
         * The service instance of [GeocodingApi].
         * This instance is lazily initialized using Retrofit.
         */
        val service: GeocodingApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GeocodingApi::class.java)
        }
    }
}