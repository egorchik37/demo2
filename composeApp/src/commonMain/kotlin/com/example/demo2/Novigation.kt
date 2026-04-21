package com.example.demo2

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.demo2.network.ApiClient
import com.example.demo2.network.AppGraph
import com.example.demo2.network.createHttpClient
import com.example.demo2.viewModels.AuthState
import com.example.demo2.viewModels.AuthViewModel
import com.example.demo2.viewModels.MainViewModel
import com.example.demo2.viewModels.TrackState
//import com.example.demo2.viewModels.TrackingViewModel
import com.example.demo2.viewModels.UiState

class WelcomScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        WelcomeScreen(
            modifier = Modifier,
            onLoginClick = { navigator.push(LoginScreen()) },
            onNavigateToRegister = { navigator.push(RegisterScreen()) })
    }
}

class RegisterScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberScreenModel { AuthViewModel(AppGraph.apiClient) }
        val state by vm.state.collectAsState()

        val isLoading = state is AuthState.Loading


        RegisterScreenUI(
            onRegisterClick = { login, pass ->
                vm.register(login, pass)
            },
            onBackToLogin = {
                navigator.pop()
            }
        )

        when (val current = state) {

            AuthState.Loading -> {
                CenterLoader(visible = isLoading)
            }

            is AuthState.Error -> {
                Text("Ошибка: ${current.message}")
            }

            else -> {}
        }

        LaunchedEffect(state) {
            if (state is AuthState.Success) {
                navigator.replace(HomeScreen(showMessage = "Вы успешно зарегистрировались ✅"))
            }
        }
    }
}


class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberScreenModel { AuthViewModel(AppGraph.apiClient) }
        val state by vm.state.collectAsState()

        val isLoading = state is AuthState.Loading

        LoginScreenUI(
            onLoginClick = { login, pass ->
                vm.login(login, pass)
                if (state is AuthState.Success) {
                    navigator.replace(HomeScreen())
                }
            },
            onNavigateToRegister = {
                navigator.push(RegisterScreen())
            }
        )

        when (val current = state) {

            AuthState.Loading -> {
                CenterLoader(visible = isLoading)
            }

            is AuthState.Error -> {
                Text("Ошибка: ${current.message}")
            }

            else -> {}
        }

        LaunchedEffect(state) {
            if (state is AuthState.Success) {
                navigator.replace(HomeScreen(showMessage = "Вы успешно вошли ✅"))
            }
        }
    }
}


class AddLinkScreen(
    private val vm: MainViewModel,
    private val art: String,
    private val platformName: String,
    private val initialTargetPrice: String,
) : Screen {

    override val key: String = "AddLink"

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val trackState by vm.trackState.collectAsState()

        // UI
        TrackSetupScreen(
            platformName = platformName,
            initialTargetPrice = initialTargetPrice,
            onSaveClick = { price, push, stock ->

                // 🔥 ВОТ ЗДЕСЬ отправка на сервер
                vm.addTracking(art)
            }
        )

        LaunchedEffect(trackState) {
            when (trackState) {

                is TrackState.Success -> {
                    navigator.replace(
                        HomeScreen("Товар добавлен ✅")
                    )
                }

                is TrackState.Error -> {
                    println((trackState as TrackState.Error).message)
                }

                else -> {}
            }
        }


    }
}

class ProductDetailsScreen(
    private val products: List<Product2>
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProductDetailsScreenUI(
            products = products,
            onBackClick = {
              navigator.pop()
            })
    }
}

class HomeScreen(
    private val showMessage: String? = null
) : Screen {
    override val key: String = "HomeScreen"


    @Composable
    override fun Content() {

        var snackbar by remember { mutableStateOf(showMessage != null) }
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberScreenModel { MainViewModel(apiClient = AppGraph.apiClient) }
        val state by vm.uiState.collectAsState()

        val isLoading = state is UiState.Loading

        MainScreenWithItems(modifier = Modifier, vm = vm)

        StyleSnackbar(
            message = showMessage ?: "",
            visible = snackbar,
            onDismiss = { snackbar = false }
        )


                            when (val state = state) {
                        is UiState.Idle -> { }
//                            Text("Введите имя и нажмите кнопку")
                        is UiState.Loading -> CenterLoader(visible = isLoading)
                        is UiState.Success ->
                            navigator?.push(
                                AddLinkScreen(
                                    vm = vm, // 🔥 передаём ViewModel
                                    art = state.data.art,
                                    platformName = state.data.name,
                                    initialTargetPrice = state.data.price
                                )
                            )
//                    Text("✅ Ответ сервера: ${state.message}")


                        is UiState.Error -> Text("❌ Ошибка: ${state.message}", color = Color.Red)
                    }

    }
}