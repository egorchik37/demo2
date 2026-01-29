package com.example.demo2

import androidx.compose.ui.graphics.Color // ⚠️ Важно: Color из compose.ui.graphics!

data class BarData(
    val label: String,
    val value: Float,
    val color: Color = Color.Gray
)