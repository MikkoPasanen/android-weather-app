package com.example.weatherapp.composables


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.model.HourlyWeather
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlin.math.roundToInt

/**
 * Composable function that displays a dialog containing a temperature chart for the current day.
 *
 * @param hourlyList List of hourly weather data.
 * @param darkMode Boolean indicating if dark mode is enabled.
 * @param onDismiss Callback function invoked when the dialog is dismissed.
 */
@Composable
fun ChartDialog(
    hourlyList: HourlyWeather,
    darkMode: Boolean,
    onDismiss: () -> Unit
) {

    val modelProducer = remember { CartesianChartModelProducer.build() }

    // X axis values
    val clock = listOf(0, 6, 12, 18, 24)

    // Y axis values
    val temperatures = hourlyList.temperature
        .filterIndexed { index, _ -> index % 4 == 0}
        .map { index -> index.roundToInt() }

    // Create a marker for the chart
    val marker = rememberDefaultCartesianMarker(
        label = rememberTextComponent(
            color = if (darkMode) Color.White else Color.Black,
            padding = Dimensions.of(10.dp),
            textSize = 20.sp
        ),
        labelPosition = DefaultCartesianMarker.LabelPosition.Top,
        indicator = rememberLayeredComponent(
            rear = rememberShapeComponent(Shape.Pill, if (darkMode) Color.White else Color.Black),
            front = rememberShapeComponent(Shape.Pill, if (darkMode) Color.White else Color.Black),
        ),
        indicatorSize = 20.dp

    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", fontSize = 20.sp)
            }
        },
        title = { Text("Temperature today") },
        text = {
            LaunchedEffect(Unit) {
                modelProducer.tryRunTransaction {
                    lineSeries {
                        series(
                            y = temperatures,
                            x = clock
                        )
                    }
                }
            }
            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(
                        label = rememberTextComponent(
                            color = if (darkMode) Color.White else Color.Black
                        ),
                    ),
                    bottomAxis = rememberBottomAxis(
                        label = rememberTextComponent(
                            color = if (darkMode) Color.White else Color.Black
                        )
                    ),
                ),
                modelProducer,
                marker = marker
            )
        }
    )
}