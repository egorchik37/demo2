package com.example.demo2

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

@Composable
fun BarChartCanvas(data: List<BarData>, modifier: Modifier) {
    val maxValue = data.maxOfOrNull { it.value } ?: 1f
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barWidth = width / data.size
        val spacing = barWidth * 0.2f
        val usableBarWidth = barWidth * 0.8f

        data.forEachIndexed { index, bar ->
            val x = index * barWidth + spacing / 2
            val barHeight = (bar.value / maxValue) * height
            val y = height - barHeight

            drawRoundRect(
                color = bar.color,
                topLeft = Offset(x, y),
                size = Size(usableBarWidth, barHeight),
                cornerRadius = CornerRadius(8f, 8f)
            )
        }
    }
}