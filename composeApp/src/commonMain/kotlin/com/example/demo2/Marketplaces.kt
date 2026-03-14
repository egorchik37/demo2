package com.example.demo2

enum class Marketplace(
    val displayName: String,
    val code: Int,
    val brandColor: Long,

){
    WILDBERRIES(
        displayName = "Wildberries",
        code = 1,
        brandColor = 0xFFCB11AB
    ),
    OZON(
        displayName = "Ozon",
        code = 2,
        brandColor = 0xFF005BFF
    ),
    YANDEX_MARKET(
        displayName = "Яндекс Маркет",
        code = 2,
        brandColor = 0xFF005BFF
    )

}