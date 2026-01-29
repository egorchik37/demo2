package com.example.demo2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform