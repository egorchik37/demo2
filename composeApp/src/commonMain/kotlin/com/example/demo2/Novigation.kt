package com.example.demo2

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                CircularProgressIndicator()
            }

            is AuthState.Error -> {
                Text("Ошибка: ${current.message}")
            }

            else -> {}
        }

        LaunchedEffect(state) {
            if (state is AuthState.Success) {
                navigator.replace(HomeScreen())
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
                CircularProgressIndicator()
            }

            is AuthState.Error -> {
                Text("Ошибка: ${current.message}")
            }

            else -> {}
        }

        LaunchedEffect(state) {
            if (state is AuthState.Success) {
                navigator.replace(HomeScreen())
            }
        }
    }
}


class AddLinkScreen(
    private val platformName: String,
    private val initialTargetPrice: String,
    private val onSaveClick: (price: String, push: Boolean, stock: Boolean) -> Unit,
) : Screen {

    override val key: String = "AddLink"

    @Composable
    override fun Content() {
        TrackSetupScreen(
            platformName = platformName,
            initialTargetPrice = initialTargetPrice,
            onSaveClick = onSaveClick
        )
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

class HomeScreen() : Screen {
    override val key: String = "HomeScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val vm = rememberScreenModel { MainViewModel(apiClient = AppGraph.apiClient) }

        MainScreenWithItems(modifier = Modifier, vm = vm)
    }
}