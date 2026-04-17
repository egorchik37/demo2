package com.example.demo2

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demo2.viewModels.MainViewModel
import com.example.demo2.viewModels.UiState

@Composable
fun GreetingScreen(viewModel: MainViewModel = viewModel()) {
    var nameInput by remember { mutableStateOf("") }

    // Собираем состояние из ViewModel (автоматически перерисовывает экран при изменении)
    val uiState by viewModel.uiState.collectAsState()

    Column {
        TextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Введите имя") }
        )

        Button(
            onClick = {
                // Вызываем метод ViewModel при клике
                if (nameInput.isNotBlank()) {
                    viewModel.fetchItem(nameInput)
                }
            },
            enabled = uiState !is UiState.Loading // Блокируем кнопку во время загрузки
        ) {
            Text("Отправить")
        }

        // Отображение результата в зависимости от состояния
        when (val state = uiState) {
            is UiState.Idle -> Text("Введите имя и нажмите кнопку")
            is UiState.Loading -> CircularProgressIndicator() // Спиннер
            is UiState.Success -> Text("✅ Ответ сервера: ${state.data}")
            is UiState.Error -> Text("❌ Ошибка: ${state.message}", color = Color.Red)
        }
    }
}