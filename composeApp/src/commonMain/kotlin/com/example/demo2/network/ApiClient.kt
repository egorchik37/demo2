package com.example.demo2.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// ---------------------------------------------------------
// 1. МОДЕЛИ ДАННЫХ
// Должны точно совпадать с полями на сервере (регистр важен!)
// ---------------------------------------------------------

@Serializable
data class UserRequest(val art: String)

@Serializable
data class UserResponse(val message: String)
// Если сервер возвращает больше полей, добавьте их сюда, например:
// data class UserResponse(val message: String, val id: Int, val status: String)

// ---------------------------------------------------------
// 2. НАСТРОЙКА КЛИЕНТА
// Создаем единый экземпляр клиента для всего приложения
// ---------------------------------------------------------

class ApiClient(private val baseUrl: String) {

    private val client = HttpClient {
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

    // ---------------------------------------------------------
    // 3. МЕТОД ЗАПРОСА
    // ---------------------------------------------------------

    /**
     * Отправляет POST запрос на указанный endpoint.
     * @param endpoint Путь относительно baseUrl (например, "greeting" или "/api/v1/user")
     * @param name Имя пользователя
     */
    suspend fun getGreeting(endpoint: String, art: String): Result<String> {
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

    // Метод для закрытия клиента (вызывать при завершении приложения, если нужно)
    fun close() {
        client.close()
    }
}