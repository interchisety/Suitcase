package com.example.suitcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.rememberNavController
import com.example.suitcase.ui.presentation.navigation.graphs.RootNavigationGraph
import com.example.suitcase.ui.theme.SuitCaseTheme
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalAnimationApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitCaseTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}