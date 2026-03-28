package com.example.demo2.viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.demo2.network.ApiClient
import com.example.demo2.network.UserRequest
import com.example.demo2.network.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val BASE_URL = "http://fastapi.tplinkdns.com:8000"
val API_ENDPOINT = "items/info" // <-- ВСТАВЬТЕ СЮДА ПРАВИЛЬНЫЙ ПУТЬ (без слэша в начале, если есть baseUrl)

class MainViewModel : ScreenModel {

    private val _uiState = MutableStateFlow("Ожидание...")
    val uiState: StateFlow<String> = _uiState

    fun fetchGreeting(Articl: String) {
        screenModelScope.launch {
            _uiState.value = "Загрузка..."

            // Вызываем наш метод
            val result = getGreeting(endpoint = API_ENDPOINT, art = Articl)

            result.onSuccess { message ->
                _uiState.value = "✅ Сервер ответил: $message"
            }.onFailure { error ->
                _uiState.value = "❌ Ошибка: ${error.message}"
            }
        }
    }

    suspend fun getGreeting(endpoint: String, art: String): Result<String> {
        val client = HttpClient {
            // Устанавливаем плагин для работы с JSON
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true      // Для красивого лога
                    isLenient = true        // Прощает мелкие ошибки JSON
                    ignoreUnknownKeys = true // Игнорирует поля, которых нет в data class
                })
            }

            // Логирование запросов и ответов (видно в Logcat / Xcode console)
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL // Заголовки, тело, статус
            }

//        // Таймауты (защита от зависания)
//        install(HttpTimeout) {
//            requestTimeoutMillis = 10_000 // 10 секунд
//            connectTimeoutMillis = 10_000
//            socketTimeoutMillis = 10_000
//        }

//        // Обработка ошибок (опционально, можно вынести отдельно)
//        defaultRequest {
//            url(baseUrl) // Базовый URL для всех запросов
//            contentType(ContentType.Application.Json)
//        }
        }
        return try {
            // Выполняем POST запрос
            val response: UserResponse = client.post(endpoint) {
                setBody(UserRequest(art = art))
                // contentType уже установлен в defaultRequest, но можно продублировать
            }.body<UserResponse>()

            // Если всё успешно, возвращаем сообщение
            Result.success(response.message)

        } catch (e: Exception) {
            // Ловим любые ошибки сети, таймауты, ошибки сериализации
            println("❌ Ошибка запроса: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
sealed class UiState {
    object Idle : UiState()          // Начальное состояние
    object Loading : UiState()       // Идет загрузка (можно показать спиннер)
    data class Success(val message: String) : UiState() // Есть данные
    data class Error(val errorMessage: String) : UiState() // Произошла ошибка
}