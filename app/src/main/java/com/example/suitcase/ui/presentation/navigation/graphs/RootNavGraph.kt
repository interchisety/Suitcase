package com.example.suitcase.ui.presentation.navigation.graphs


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.suitcase.ui.presentation.navigation.HomeRoute
import com.example.suitcase.ui.presentation.navigation.LoginRoute
import com.example.suitcase.ui.presentation.navigation.OnboardRoute
import com.example.suitcase.ui.presentation.navigation.RootRoute
import com.example.suitcase.ui.presentation.navigation.SignupRoute
import com.example.suitcase.ui.presentation.screens.AnimatedSplashScreen
import com.example.suitcase.ui.presentation.screens.listitems.homescreens.HomeScreen
import com.example.suitcase.ui.presentation.screens.onboarding.OnBoardingScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun RootNavigationGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route = "root",
        startDestination = RootRoute.route
    ){
        composable(route = RootRoute.route){
            AnimatedSplashScreen{
                navController.popBackStack()
                navController.navigate(OnboardRoute.route)
            }
        }
        composable(route = OnboardRoute.route){
            OnBoardingScreen(
                onSignUpClick = {navController.navigate(SignupRoute.route)} ,
                onLoginClick = { navController.navigate(LoginRoute.route)}
            )
        }
        authenticateNavGraph(baseNavController = navController)
        composable(route = HomeRoute.route){
           HomeScreen()
        }

    }
}
