package com.example.demo2

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Основные цвета
private val Color.Companion.LightThemeLiteGray: Color get() = Color(0xFFF4F4F4)
private val Color.Companion.LightThemeSecondaryGray: Color get() = Color(0xFFC7C7C7)
private val Color.Companion.LightThemePrimaryGray: Color get() = Color(0xFFB3B3B3)
private val Color.Companion.LightThemeDarkGray: Color get() = Color(0xFF737373)

private val Color.Companion.LightThemeContainerColor: Color get() = Color(0xFFE0E0E0)




val ColorScheme.backgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.White
    }

val ColorScheme.liteGray
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.LightThemeLiteGray
    } else {
        Color.LightThemeLiteGray
    }

val ColorScheme.primaryGray
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.LightThemePrimaryGray
    } else {
        Color.LightThemePrimaryGray
    }

val ColorScheme.secondaryGray
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.LightThemeSecondaryGray
    } else {
        Color.LightThemeSecondaryGray
    }

val ColorScheme.black
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.Black
    } else {
        Color.Black
    }

val ColorScheme.darkGray
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.LightThemeDarkGray
    } else {
        Color.LightThemeDarkGray
    }

//val ColorScheme.liteWhite
//    @Composable
//    get() = if (isSystemInDarkTheme()) {
//        Color.LightThemeLiteWhite
//    } else {
//        Color.LightThemeLiteWhite
//    }

val ColorScheme.ContainerColor
    @Composable
    get() = if (isSystemInDarkTheme()) {
        Color.LightThemeContainerColor
    } else {
        Color.LightThemeContainerColor
    }