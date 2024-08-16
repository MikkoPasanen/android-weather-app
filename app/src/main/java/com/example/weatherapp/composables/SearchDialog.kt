package com.example.weatherapp.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.model.CityData
import com.example.weatherapp.model.LocationData
import com.example.weatherapp.viewmodel.GeocodingViewModel

/**
 * Composable function to display a search dialog for cities.
 *
 * @param onDismiss Callback function invoked when the dialog is dismissed.
 * @param onCitySelect Callback function invoked when a city is selected from the search results.
 */
@Composable
fun SearchDialog(
    onDismiss: () -> Unit,
    onCitySelect: (LocationData, String) -> Unit)
{
    val geocodingViewModel: GeocodingViewModel = viewModel()
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var apiError by remember { mutableStateOf(false) }
    var cityData by remember { mutableStateOf<List<CityData>>(emptyList()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", fontSize = 20.sp)
            }
        },
        title = { Text("Search for cities") },
        text = {
            Column(
                modifier = Modifier.heightIn(max = 400.dp)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Enter city name") },
                    shape = RoundedCornerShape(8.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        // When clicked, fetch city data with the given text input
                        // and handle possible errors from the API
                        onClick = {
                            isLoading = true
                            apiError = false
                            geocodingViewModel.fetchCities(searchText) { loading, cityResponse, error ->
                                isLoading = loading
                                cityData = cityResponse
                                error?.let { apiError = true}
                            }
                        },
                    ) {
                        Text("Search", fontSize = 20.sp, modifier = Modifier.padding(top = 10.dp))
                    }
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier
                            .padding(top = 30.dp)
                            .size(40.dp))
                    }
                } else {
                    if (apiError) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No results with given text",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(top = 15.dp)
                        ) {
                            items(cityData) { city ->
                                Box(
                                    modifier = Modifier.clickable {
                                        onCitySelect(LocationData(city.latitude, city.longitude), city.name)
                                        onDismiss()
                                    }.fillMaxWidth()
                                ) {
                                    Column {
                                        Text("${city.name}, ${city.administrative}", fontSize = 18.sp)
                                        Text(city.country, fontSize = 16.sp)
                                    }
                                }

                                Divider(
                                    color = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}