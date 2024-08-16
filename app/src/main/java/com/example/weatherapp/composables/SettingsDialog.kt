package com.example.weatherapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.viewmodel.SettingsViewModel

/**
 * Composable function to display a settings dialog for the weather app.
 *
 * @param settingsViewModel ViewModel containing settings data.
 * @param onDismiss Callback function invoked when the dialog is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    settingsViewModel: SettingsViewModel,
    onDismiss: () -> Unit
) {
    var expandedTemp by remember { mutableStateOf(false) }
    var expandedMode by remember { mutableStateOf(false) }
    var expandedType by remember { mutableStateOf(false) }

    val temperatureUnits = arrayOf("Celsius(°C)", "Fahrenheit(°F)")
    val colorModes = arrayOf("Darkmode", "Lightmode")
    val measurementTypes = arrayOf("Metric", "Imperial")

    val selectedTemperature by settingsViewModel.tempUnit
    val selectedColorMode by settingsViewModel.colorMode
    val selectedMeasurementType by settingsViewModel.measurementType

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", fontSize = 20.sp)
            }
        },
        title = { Text("Settings") },
        text = {
            Column {
                Text("Temperature unit", Modifier.padding(bottom = 2.dp), fontSize = 18.sp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedTemp,
                        onExpandedChange = {
                            expandedTemp = !expandedTemp
                        }
                    ) {
                        TextField(
                            value = selectedTemperature,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTemp) },
                            modifier = Modifier.menuAnchor(),
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTemp,
                            onDismissRequest = { expandedTemp = false }
                        ) {
                            temperatureUnits.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        settingsViewModel.setTempUnit(item)
                                        expandedTemp = false
                                    }
                                )
                            }
                        }
                    }
                }

                Text("Measurement type", Modifier.padding(bottom = 2.dp), fontSize = 18.sp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedType,
                        onExpandedChange = {
                            expandedType = !expandedType
                        }
                    ) {
                        TextField(
                            value = selectedMeasurementType,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedType) },
                            modifier = Modifier.menuAnchor(),
                        )

                        ExposedDropdownMenu(
                            expanded = expandedType,
                            onDismissRequest = { expandedType = false }
                        ) {
                            measurementTypes.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        settingsViewModel.setMeasurementType(item)
                                        expandedType = false
                                    }
                                )
                            }
                        }
                    }
                }

                Text("Color mode", Modifier.padding(bottom = 2.dp), fontSize = 18.sp)
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedMode,
                        onExpandedChange = {
                            expandedMode = !expandedMode
                        }
                    ) {
                        TextField(
                            value = selectedColorMode,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMode) },
                            modifier = Modifier.menuAnchor(),
                        )

                        ExposedDropdownMenu(
                            expanded = expandedMode,
                            onDismissRequest = { expandedMode = false }
                        ) {
                            colorModes.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        settingsViewModel.setColorMode(item)
                                        expandedMode = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}