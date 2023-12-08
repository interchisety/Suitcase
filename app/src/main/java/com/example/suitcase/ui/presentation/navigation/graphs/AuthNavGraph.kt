package com.example.suitcase.ui.presentation.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.suitcase.ui.presentation.navigation.ForgotRoute
import com.example.suitcase.ui.presentation.navigation.HomeRoute
import com.example.suitcase.ui.presentation.navigation.LoginRoute
import com.example.suitcase.ui.presentation.navigation.SignupRoute
import com.example.suitcase.ui.presentation.screens.authentication.ForgetPasswordScreen
import com.example.suitcase.ui.presentation.screens.authentication.LoginScreen
import com.example.suitcase.ui.presentation.screens.authentication.SignUpScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
fun NavGraphBuilder.authenticateNavGraph(baseNavController: NavHostController){
    navigation(
        route = "auth",
        startDestination = LoginRoute.route
    ){
        composable(route = LoginRoute.route) {
            LoginScreen(
                onGoBackClick={
                    baseNavController.popBackStack()
                },
                onLoginClick = {
                    baseNavController.popBackStack()
                    baseNavController.navigate(HomeRoute.route)
                },
                onSignUpClick = {baseNavController.navigate(SignupRoute.route)} ,
                onForgotClick = { baseNavController.navigate(ForgotRoute.route)}
            )
        }
        composable(route = SignupRoute.route){
            SignUpScreen(
                onGoBackClick={
                    baseNavController.popBackStack()
                },
                onRegisterclick = {
                    baseNavController.popBackStack()
                    baseNavController.navigate(LoginRoute.route)
                } ,
                onLoginClick = { baseNavController.navigate(LoginRoute.route)}
            )
        }
        composable(route = ForgotRoute.route){
            ForgetPasswordScreen(
                onGoBackClick={
                    baseNavController.popBackStack()
                },
                onLoginClick = {
                    baseNavController.popBackStack()
                    baseNavController.navigate(LoginRoute.route)
                }
            )
        }
    }
}