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
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.arrow
import demo2.composeapp.generated.resources.autorenew_24px
import demo2.composeapp.generated.resources.home_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun TrackSetupScreen(
    platformName: String,
    isPushEnabled: Boolean,
    onPushToggle: (Boolean) -> Unit,
    targetPrice: String,
    onTargetPriceChange: (String) -> Unit,
    isStockTrackingEnabled: Boolean,
    onStockTrackingToggle: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            // Заголовок с названием платформы
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Отслеживание на $platformName",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant                    )
                }
            }

            // Push-уведомления
            SettingSwitchRow(
                title = "Получать push-уведомления",
                isChecked = isPushEnabled,
                onCheckedChange = onPushToggle
            )

            // Пороговая цена
            OutlinedTextField(
                value = targetPrice,
                onValueChange = onTargetPriceChange,
                label = { Text("Уведомлять при цене ≤") },
                placeholder = { Text("Например: 4990") },
                shape = RoundedCornerShape(15.dp),
//                keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
//                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
//                )
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                ,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Отслеживание наличия
            SettingSwitchRow(
                title = "Уведомлять о появлении в наличии",
                isChecked = isStockTrackingEnabled,
                onCheckedChange = onStockTrackingToggle
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка действия
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Начать отслеживание",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }    }
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