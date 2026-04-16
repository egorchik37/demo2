package com.example.demo2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.demo2.network.ApiClient
import com.example.demo2.network.AppGraph
import com.example.demo2.network.createHttpClient
import com.example.demo2.viewModels.MainViewModel

class AddLink(
    private val platrormName: String,
    private val isPushEnabled: Boolean,
    private val onPushToggle: (Boolean) -> Unit,
    private val targetPrice: String,
    private val onTargetPriceChange: (String) -> Unit,
    private val isStockTrackingEnabled: Boolean,
    private val onStockTrackingToggle: (Boolean) -> Unit,
    private val onSaveClick: () -> Unit,) : Screen {

    override val key: String = "AddLink"

    @Composable
    override fun Content(){
       TrackSetupScreen(platformName = platrormName,
           isPushEnabled = isPushEnabled,
           onPushToggle = onPushToggle,
           targetPrice = targetPrice,
           onTargetPriceChange = onTargetPriceChange,
           isStockTrackingEnabled = isStockTrackingEnabled,
           onStockTrackingToggle = onStockTrackingToggle,
           onSaveClick = onSaveClick)
    }
}

class HomeScreen() : Screen {
    override val key: String = "HomeScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val vm = rememberScreenModel{ MainViewModel(apiClient = AppGraph.apiClient) }

        MainScreenWithItems(modifier = Modifier, vm = vm)
    }
}