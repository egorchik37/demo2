package com.example.demo2.viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.demo2.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

val BASE_URL = "http://fastapi.tplinkdns.com:8000"
val API_ENDPOINT = "items/info" // <-- ВСТАВЬТЕ СЮДА ПРАВИЛЬНЫЙ ПУТЬ (без слэша в начале, если есть baseUrl)

val apiClient = ApiClient(baseUrl = BASE_URL)

class MainViewModel : ScreenModel {

    private val _uiState = MutableStateFlow("Ожидание...")
    val uiState: StateFlow<String> = _uiState

    fun fetchGreeting(Articl: String) {
        screenModelScope.launch {
            _uiState.value = "Загрузка..."

            // Вызываем наш метод
            val result = apiClient.getGreeting(endpoint = API_ENDPOINT, art = Articl)

            result.onSuccess { message ->
                _uiState.value = "✅ Сервер ответил: $message"
            }.onFailure { error ->
                _uiState.value = "❌ Ошибка: ${error.message}"
            }
        }
    }
}
sealed class UiState {
    object Idle : UiState()          // Начальное состояние
    object Loading : UiState()       // Идет загрузка (можно показать спиннер)
    data class Success(val message: String) : UiState() // Есть данные
    data class Error(val errorMessage: String) : UiState() // Произошла ошибка
}