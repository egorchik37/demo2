package com.example.demo2.network

import io.ktor.client.*
import io.ktor.client.call.*  // <--- Важно! Для метода body<T>()
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val name: String)

@Serializable
data class UserResponse(val message: String)

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getGreeting(name: String): String {
    return try {
        // Правильный эндпоинт + вызов body<T>() для десериализации
        val response: UserResponse = client.post("http://fastapi.tplinkdns.com:8000/docs") {
            setBody(UserRequest(name = name))
            contentType(io.ktor.http.ContentType.Application.Json) // Опционально, но полезно
        }.body<UserResponse>() // <--- Ключевой момент!

        response.message
    } catch (e: Exception) {
        "Ошибка: ${e.message}"
    }
}