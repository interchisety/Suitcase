package com.example.suitcase.ui.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = HomeRoute.route,
        title = HomeRoute.title,
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = ListRoute.route,
        title = ListRoute.title,
        icon = Icons.Default.List
    )
}