package com.kfouri.cabifychallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kfouri.cabifychallenge.ui.currentOrder.CurrentOrderViewModel
import com.kfouri.cabifychallenge.ui.theme.CabifyChallengeTheme
import com.kfouri.cabifychallenge.ui.productList.ProductListViewModel
import com.kfouri.cabifychallenge.ui.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val productListViewModel: ProductListViewModel by viewModels()
    private val currentOrderViewModel: CurrentOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CabifyChallengeTheme {
                MainScreen(productListViewModel, currentOrderViewModel)
            }
        }
        screenSplash.setKeepOnScreenCondition { false }
    }
}