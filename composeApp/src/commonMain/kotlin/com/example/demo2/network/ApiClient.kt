package com.example.demo2.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// --------------------
// MODELS
// --------------------

@Serializable
data class UserRequest(
    val art: String
)

@Serializable
data class UserResponse(
    val message: String
)

// --------------------
// API CLIENT
// --------------------

class ApiClient(
    private val baseUrl: String,
    private val client: HttpClient
) {

    suspend fun getGreeting(
        endpoint: String,
        art: String
    ): Result<String> {

        return try {

            val response: UserResponse = client.post {
                url("$baseUrl/$endpoint")
                contentType(ContentType.Application.Json)
                setBody(UserRequest(art))
            }.body()

            Result.success(response.message)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

// --------------------
// HTTP CLIENT FACTORY (commonMain)
// --------------------

fun createHttpClient(): HttpClient {
    return HttpClient {

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}