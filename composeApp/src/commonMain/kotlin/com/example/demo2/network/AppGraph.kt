package com.example.demo2.network


import com.example.demo2.network.ApiClient
import com.example.demo2.network.createHttpClient

object AppGraph {

    private val httpClient = createHttpClient()

    val apiClient = ApiClient(
        baseUrl = "http://fastapi.tplinkdns.com:8000",
        client = httpClient
    )
}

object TokenStorage {
    private var token: String? = null

    fun save(newToken: String) {
        token = newToken
    }

    fun get(): String? = token
}