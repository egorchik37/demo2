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

sealed class TrackState {
    object Idle : TrackState()
    object Loading : TrackState()
    object Success : TrackState()
    data class Error(val message: String) : TrackState()
}

//class TrackingViewModel(
//    private val apiClient: ApiClient
//) : ScreenModel {
//
//    private val _state = MutableStateFlow<TrackState>(TrackState.Idle)
//    val state: StateFlow<TrackState> = _state
//
//    fun addTracking(art: String) {
//        screenModelScope.launch {
//
//            _state.value = TrackState.Loading
//
//            val token = TokenStorage.get()
//
//            if (token.isNullOrEmpty()) {
//                _state.value = TrackState.Error("Нет токена")
//                return@launch
//            }
//
//            val result = apiClient.addTrackingItem(
//                endpoint = "items/add",
//                token = token,
//                art = art
//            )
//
//            result.onSuccess { success ->
//                _state.value = if (success) {
//                    TrackState.Success
//                } else {
//                    TrackState.Error("Не удалось добавить")
//                }
//            }.onFailure {
//                _state.value = TrackState.Error(it.message ?: "Ошибка")
//            }
//        }
//    }
//}



class AuthViewModel(
    private val apiClient: ApiClient
) : ScreenModel {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(username: String, password: String) {
        screenModelScope.launch {

            _state.value = AuthState.Loading

            val result = apiClient.login(LOGIN_ENDPOINT, username, password )

            result.onSuccess { result ->
                // 👉 сохраняем токен
                TokenStorage.save(result.token)
                println(result)
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

    private val _trackState = MutableStateFlow<TrackState>(TrackState.Idle)

    val trackState: StateFlow<TrackState> = _trackState
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
    fun addTracking(art: String) {
        screenModelScope.launch {

            _trackState.value = TrackState.Loading

            val token = TokenStorage.get()

            if (token.isNullOrEmpty()) {
                _trackState.value = TrackState.Error("Нет токена")
                return@launch
            }

            val result = apiClient.addTrackingItem(
                endpoint = "items/track",
                token = token,
                art = art
            )

            result.onSuccess { success ->
                _trackState.value = if (success) {
                    TrackState.Success
                } else {
                    TrackState.Error("Не удалось добавить")
                }
            }.onFailure {
                _trackState.value = TrackState.Error(it.message ?: "Ошибка")
            }
        }
    }

    companion object {
        const val API_ENDPOINT = "items/info"
    }
}