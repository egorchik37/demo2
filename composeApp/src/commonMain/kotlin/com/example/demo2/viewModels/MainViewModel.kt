package com.example.demo2.viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.demo2.network.ApiClient
import com.example.demo2.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// -------------------------
// UI STATE (нормальный вариант)
// -------------------------

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val message: UserResponse) : UiState()
    data class Error(val message: String) : UiState()
}

// -------------------------
// VIEWMODEL (ScreenModel)
// -------------------------

class MainViewModel(
    private val apiClient: ApiClient
) : ScreenModel {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchGreeting(article: String) {
        screenModelScope.launch {

            _uiState.value = UiState.Loading

            val result = apiClient.getGreeting(
                endpoint = API_ENDPOINT,
                art = article
            )

            result.onSuccess { message ->
                _uiState.value = UiState.Success(message)
            }.onFailure { error ->
                _uiState.value = UiState.Error(
                    error.message ?: "Unknown error"
                )
            }
        }
    }

    companion object {
        const val BASE_URL = "http://fastapi.tplinkdns.com:8000"
        const val API_ENDPOINT = "items/info"
    }
}