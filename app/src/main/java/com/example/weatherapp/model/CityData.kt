package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

/**
 * Where the Geocoding API response is parsed into.
 *
 * @property cities A list of cities and their info.
 */
data class CityResponse(
    @SerializedName("results")
    val cities: List<CityData>
)

/**
 * Single citys data.
 *
 * @property latitude latitude.
 * @property longitude longitude.
 * @property name name of the city.
 * @property country the country in which the city is in.
 * @property administrative extra info about city location for example a county.
 */
data class CityData(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("name")
    val name: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("admin1")
    val administrative: String,
)