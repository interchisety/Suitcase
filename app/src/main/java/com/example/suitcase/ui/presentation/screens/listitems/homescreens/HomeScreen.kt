package com.example.suitcase.ui.presentation.screens.listitems.homescreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.suitcase.ui.presentation.navigation.AddListRoute
import com.example.suitcase.ui.presentation.navigation.BottomBarScreen
import com.example.suitcase.ui.presentation.navigation.HomeRoute
import com.example.suitcase.ui.presentation.screens.listitems.itemscreens.addItemRoute
import com.example.suitcase.ui.presentation.screens.listitems.listscreens.addNewListRoute
import com.example.suitcase.ui.presentation.screens.addPageUnderSettingsRoute
import com.example.suitcase.ui.presentation.screens.addProfileRoute
import com.example.suitcase.ui.presentation.screens.addSettingsRoute
import com.example.suitcase.ui.presentation.screens.addTestRoute
import com.example.suitcase.ui.presentation.screens.listDetailsRoute
import com.example.suitcase.ui.viewmodal.NewListViewModal

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { HomeListBottomBarComponent(navController = navController) },
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = HomeRoute.route) {
                addHomeRoute(baseNavController = navController)
                addProfileRoute()
                addTestRoute(navController = navController)
                addSecondNav(baseNavController = navController)
            }
        }
    }
}




private fun NavGraphBuilder.addSecondNav(baseNavController: NavHostController) {
    composable(
        route = AddListRoute.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val navController = rememberNavController()
        val viewmodel = viewModel<NewListViewModal>()
        NavHost(navController = navController, startDestination = AddListRoute.route) {
            addNewListRoute(
                viewmodel = viewmodel,
                baseNavController = baseNavController,
                navController = navController
            )
            listDetailsRoute(
                viewmodel = viewmodel,
                navController = navController
            )
            addSettingsRoute(
                baseNavController = baseNavController,
                navController = navController
            )
            addItemRoute(navController = navController)
            addPageUnderSettingsRoute(navController = navController)
        }
    }
}