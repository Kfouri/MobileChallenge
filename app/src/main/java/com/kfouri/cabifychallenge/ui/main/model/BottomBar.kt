package com.kfouri.cabifychallenge.ui.main.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarOptions(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val badge: Boolean
) {

    object Products: BottomBarOptions(
        route = "products",
        title = "Products",
        icon = Icons.Default.Add,
        badge = false
    )

    object CurrentOrder: BottomBarOptions(
        route = "currentOrder",
        title = "Shopping Cart",
        icon = Icons.Default.ShoppingCart,
        badge = true
    )
}