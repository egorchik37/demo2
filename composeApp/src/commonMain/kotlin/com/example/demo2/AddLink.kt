package com.example.demo2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.arrow
import demo2.composeapp.generated.resources.autorenew_24px
import demo2.composeapp.generated.resources.home_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TrackSetupScreen(
    platformName: String,
    initialTargetPrice: String,
    onSaveClick: (price: String, push: Boolean, stock: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val navigator = LocalNavigator.currentOrThrow
    var isPushEnabled by remember { mutableStateOf(false) }
    var targetPrice by remember { mutableStateOf(initialTargetPrice) }
    var isStockTrackingEnabled by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Заголовок
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Отслеживать: $platformName",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Push
            SettingSwitchRow(
                title = "Получать push-уведомления",
                isChecked = isPushEnabled,
                onCheckedChange = { isPushEnabled = it }
            )

            // Цена
            OutlinedTextField(
                value = targetPrice,
                onValueChange = { targetPrice = it },
                label = { Text("Уведомлять при цене ≤") },
                placeholder = { Text("Например: 4990") },
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Наличие
            SettingSwitchRow(
                title = "Уведомлять о появлении в наличии",
                isChecked = isStockTrackingEnabled,
                onCheckedChange = { isStockTrackingEnabled = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка
            Button(
                onClick = {

//                    val testData = listOf(
//                        Product2("Шины R16", "5000", 4.8, true, "01.04"),
//                        Product2("Шины R16", "4800", 4.8, true, "05.04"),
//                        Product2("Шины R16", "4500", 4.9, true, "10.04"),
//                        Product2("Шины R16", "4299", 4.9, true, "15.04")
//                    )

                    onSaveClick(targetPrice, isPushEnabled, isStockTrackingEnabled)
//                    navigator.push(ProductDetailsScreen(testData))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Начать отслеживание")
            }
        }
    }
}

// Вспомогательный компонент для строк с переключателем
@Composable
private fun SettingSwitchRow(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            thumbContent = if (isChecked) { {  } } else { null }
        )
    }
}