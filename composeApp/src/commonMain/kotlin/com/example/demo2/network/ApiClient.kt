package com.example.demo2.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive

// --------------------
// MODELS
// --------------------
@Serializable
data class RegisterRequest(
    val username: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val message: String
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val token: String
)

@Serializable
data class UserRequest(
    val art: String
)
@Serializable
data class UserResponse(
    val price: String,
    val name: String,
    val available: Boolean,
    val rating: Double
)

// --------------------
// API CLIENT
// --------------------

class ApiClient(
    private val baseUrl: String,
    private val client: HttpClient
) {

    suspend fun register(
        endpoint: String,
        login: String,
        password: String
    ): Result<Unit> {

        return try {

            client.post {
                url("$baseUrl/$endpoint")
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(login, password))
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun login(
        endpoint: String,
        login: String,
        password: String
    ): Result<LoginResponse> {
        return try {

            val response: LoginResponse = client.get {
                url("$baseUrl/$endpoint")
                parameter("login", login)
                parameter("password", password)

                header("Accept", "*/*")
                header("User-Agent", "Mozilla/5.0")
            }.body()

            if (response.success && response.token != null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Неверный логин или пароль"))
            }


//            Result.success(raw)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGreeting(
        endpoint: String,
        art: String
    ): Result<UserResponse> {

        return try {
            val raw: List<JsonElement> = client.get {
                url("$baseUrl/$endpoint")
                parameter("art", art)
            }.body()

            // Преобразуем в объект
            val response = UserResponse(
                price = raw[0].jsonPrimitive.content, // 🔥 всегда строка
                name = raw[1].jsonPrimitive.content,
                available = raw[2].jsonPrimitive.boolean,
                rating = raw[3].jsonPrimitive.double
            )


            Result.success(response)

//            val response: UserResponse = client.get {
//                url("$baseUrl/$endpoint")
//                parameter("art", art)
//            }.body()
////            val response: UserResponse = client.post {
////                url("$baseUrl/$endpoint")
////                contentType(ContentType.Application.Json)
////                setBody(UserRequest(art))
////            }.body()
//            Result.success(response)
////            Result.success(response.message)

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

        install(HttpTimeout) {
            requestTimeoutMillis = 120_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 30_000
        }
    }
}