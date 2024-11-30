package com.lowjunee.healthsafe.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lowjunee.healthsafe.ui.theme.Black

@Composable
fun MetricsGraph(
    data: List<Float>, // Metric data (e.g., heart rate, steps, etc.)
    yRange: ClosedFloatingPointRange<Float>, // The minimum and maximum values on the Y-axis
    xLabels: List<String> = emptyList(), // Optional X-axis labels (e.g., time)
    color: Color = Color.Red, // Graph color
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        if (data.isNotEmpty() && xLabels.isNotEmpty()) {
            val yMin = yRange.start
            val yMax = yRange.endInclusive

            // Prevent division by zero
            val range = if (yMax - yMin == 0f) 1f else yMax - yMin
            val normalizedData = data.map { (it - yMin) / range }

            // Leave space for X-axis labels
            val labelOffset = 20.dp.toPx()

            // Draw graph lines
            for (i in 1 until normalizedData.size) {
                val x1 = size.width * (i - 1) / (normalizedData.size - 1)
                val y1 = (size.height - labelOffset) * (1 - normalizedData[i - 1])
                val x2 = size.width * i / (normalizedData.size - 1)
                val y2 = (size.height - labelOffset) * (1 - normalizedData[i])

                drawLine(
                    color = color,
                    start = androidx.compose.ui.geometry.Offset(x1, y1),
                    end = androidx.compose.ui.geometry.Offset(x2, y2),
                    strokeWidth = 4f
                )
            }

            // Draw X-axis labels (every 4-hour increment)
            val interval = 4
            xLabels.forEachIndexed { index, label ->
                if (index % interval == 0) { // Show label only for every 4-hour interval
                    val x = size.width * index / (xLabels.size - 1)
                    drawContext.canvas.nativeCanvas.apply {
                        val paint = android.graphics.Paint().apply {
                            this.color = Black.toArgb() // Use BlackColor from theme
                            textSize = 12.sp.toPx() // Convert sp to px
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                        drawText(
                            label,
                            x,
                            size.height - 5.dp.toPx(), // Adjust label position
                            paint
                        )
                    }
                }
            }
        }
    }
}
