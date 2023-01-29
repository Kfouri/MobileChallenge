package com.kfouri.cabifychallenge.ui.main

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kfouri.cabifychallenge.ui.currentOrder.CurrentOrderScreen
import com.kfouri.cabifychallenge.ui.currentOrder.CurrentOrderViewModel
import com.kfouri.cabifychallenge.ui.currentOrder.model.CurrentOrderUiState
import com.kfouri.cabifychallenge.ui.productList.ProductListScreen
import com.kfouri.cabifychallenge.ui.main.model.BottomBarOptions
import com.kfouri.cabifychallenge.ui.productList.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    productListViewModel: ProductListViewModel,
    currentOrderViewModel: CurrentOrderViewModel
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<CurrentOrderUiState>(
        initialValue = CurrentOrderUiState.Loading,
        key1 = lifecycle,
        key2 = currentOrderViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            currentOrderViewModel.uiState.collect {
                value = it
            }
        }
    }

    val navController = rememberNavController()
    var productCount = 0

    if (uiState is CurrentOrderUiState.Success) {
        productCount = currentOrderViewModel.getItemsOrderCount((uiState as CurrentOrderUiState.Success).data.products)
    }

    Scaffold(
        bottomBar = { BottomBar(navController, productCount) }
    ) {
        BottomNavGraph(
            navController = navController,
            productListViewModel,
            currentOrderViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    navController: NavHostController,
    productCount: Int
) {
    val screens = listOf(
        BottomBarOptions.Products,
        BottomBarOptions.CurrentOrder,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar() {
        screens.forEach { screen ->

            val selected = screen.route == navBackStackEntry?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(
                        text = screen.title,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    if (screen.badge && productCount > 0) {
                        BadgedBox(badge = { Badge { Text("$productCount") } }) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = "${screen.title} Icon",
                            )
                        }
                    } else {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = "${screen.title} Icon",
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    productListViewModel: ProductListViewModel,
    currentOrderViewModel: CurrentOrderViewModel
) {

    NavHost(navController = navController, startDestination = BottomBarOptions.Products.route) {

        composable(route = BottomBarOptions.Products.route) {
            ProductListScreen(productListViewModel)
        }

        composable(route = BottomBarOptions.CurrentOrder.route) {
            CurrentOrderScreen(currentOrderViewModel)
        }
    }
}