package com.example.suitcase.ui.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.navigation.SettingsRoute
import com.example.suitcase.ui.presentation.navigation.TestRoute

@Composable
fun TestScreen(onButtonClick: () -> Unit) {
    Column {
        Text(
            stringResource(id = R.string.test_screen_title),
            style = MaterialTheme.typography.displayMedium
        )
        Button(onClick = onButtonClick) {
            Text("To settings page")
        }
    }
}

fun NavGraphBuilder.addTestRoute(
    navController: NavHostController
) {
    composable(
        route = TestRoute.route,
        exitTransition = {
            when (targetState.destination.route) {
                SettingsRoute.route -> {
                    slideOutHorizontally(
                        targetOffsetX = { -300 }, animationSpec = tween(
                            durationMillis = 300, easing = FastOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                }

                else -> null
            }

        },
        popEnterTransition = {
            when (initialState.destination.route) {
                SettingsRoute.route -> {
                    slideInHorizontally(
                        initialOffsetX = { -300 }, animationSpec = tween(
                            durationMillis = 300, easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(animationSpec = tween(300))
                }

                else -> null
            }

        },
    ) {
        TestScreen(onButtonClick = {
            navController.navigate(
                route = SettingsRoute.route
            )
        })
    }
}