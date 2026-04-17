package com.example.demo2.viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.demo2.network.ApiClient
import com.example.demo2.network.TokenStorage
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
    data class Success(val data: UserResponse) : UiState()
    data class Error(val message: String) : UiState()
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}



class AuthViewModel(
    private val apiClient: ApiClient
) : ScreenModel {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(username: String, password: String) {
        screenModelScope.launch {

            _state.value = AuthState.Loading

            val result = apiClient.login(LOGIN_ENDPOINT, username, password )

            result.onSuccess { token ->
                // 👉 сохраняем токен
                TokenStorage.save(token)

                _state.value = AuthState.Success
            }.onFailure {
                _state.value = AuthState.Error(it.message ?: "Error")
            }
        }
    }
    fun register(username: String, password: String) {
        screenModelScope.launch {

            _state.value = AuthState.Loading

            val result = apiClient.register(REGISTER_ENDPOINT, username, password)

            result.onSuccess {
                _state.value = AuthState.Success
            }.onFailure {
                _state.value = AuthState.Error(it.message ?: "Ошибка регистрации")
            }
        }
    }

    companion object {
        const val LOGIN_ENDPOINT = "users/log_in"
        const val REGISTER_ENDPOINT = "users/create"

    }
}

// -------------------------
// VIEWMODEL (ScreenModel)
// -------------------------

class MainViewModel(
    private val apiClient: ApiClient
) : ScreenModel {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchItem(article: String) {
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
        const val API_ENDPOINT = "items/info"
    }
}