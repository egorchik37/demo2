@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.demo2

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.arrow_forward_24px
import org.jetbrains.compose.resources.painterResource

// --------------------
// MODEL
// --------------------

data class Product2(
    val name: String,
    val price: String,
    val rating: Double,
    val available: Boolean,
    val date: String
)

// --------------------
// SCREEN
// --------------------

@Composable
fun ProductDetailsScreenUI(
    products: List<Product2>,
    onBackClick: () -> Unit
) {

    val current = products.lastOrNull() ?: return

    val prices = products.mapNotNull {
        it.price.replace("₽", "")
            .replace(" ", "")
            .replace(",", ".")
            .toFloatOrNull()
    }

    val isGrowing = prices.lastOrNull() ?: 0f >= prices.firstOrNull() ?: 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(current.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                          painter = painterResource(Res.drawable.arrow_forward_24px),
                            contentDescription = "Назад",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 📈 ГРАФИК (главный элемент)
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text(
                        text = if (isGrowing) "📈 Рост цены" else "📉 Падение цены",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isGrowing)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PriceChart(
                        prices = prices,
                        isGrowing = isGrowing
                    )
                }
            }

            // 📦 ИНФО (внизу, красиво и компактно)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                InfoChip(
                    title = "Цена",
                    value = "${current.price} ₽",
                    modifier = Modifier.weight(1f)
                )

                InfoChip(
                    title = "Рейтинг",
                    value = "⭐ ${current.rating}",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoChip(
                    title = "Наличие",
                    value = if (current.available) "Есть" else "Нет",
                    color = if (current.available)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// --------------------
// CHIP
// --------------------

@Composable
fun InfoChip(
    title: String,
    value: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelSmall)
            Text(
                value,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
        }
    }
}

// --------------------
// ГРАФИК С АНИМАЦИЕЙ
// --------------------

@Composable
fun PriceChart(
    prices: List<Float>,
    isGrowing: Boolean
) {

    val lineColor = if (isGrowing) Color(0xFF4CAF50) else Color(0xFFF44336)

    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000),
        label = ""
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp) // 🔥 компактнее
    ) {

        if (prices.size < 2) return@Canvas

        val max = prices.max()
        val min = prices.min()
        val range = (max - min).takeIf { it != 0f } ?: 1f

        val stepX = size.width / (prices.size - 1)

        val points = prices.mapIndexed { i, price ->
            val x = i * stepX
            val y = size.height - ((price - min) / range * size.height)
            Offset(x, y)
        }

        val visible = (points.size * progress).toInt().coerceAtLeast(1)
        val visiblePoints = points.take(visible)

        for (i in 0 until visiblePoints.size - 1) {
            drawLine(
                color = lineColor,
                start = visiblePoints[i],
                end = visiblePoints[i + 1],
                strokeWidth = 5f,
                cap = StrokeCap.Round
            )
        }

        visiblePoints.forEach {
            drawCircle(
                color = lineColor,
                radius = 5f,
                center = it
            )
        }
    }
}