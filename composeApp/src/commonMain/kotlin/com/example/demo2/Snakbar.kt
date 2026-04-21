package com.example.demo2

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun StyleSnackbar(
    message: String,
    visible: Boolean,
    onDismiss: () -> Unit
) {
    if (!visible) return

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2500)
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Surface(
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 6.dp,
            color = Color(0xFF2A2A2A),
            modifier = Modifier
                .fillMaxWidth()                  // 🔥 почти на всю ширину
                .padding(horizontal = 12.dp)     // маленькие отступы
                .padding(bottom = 16.dp)         // отступ от навбара
                .graphicsLayer { this.alpha = alpha }
        ) {
            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}