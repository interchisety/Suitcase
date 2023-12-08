package com.example.suitcase.ui.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suitcase.R
import com.example.suitcase.ui.theme.dark_onPrimaryContainer
import com.example.suitcase.ui.theme.dark_primary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_background
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_onPrimaryContainer
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.light_primaryContainer
import kotlinx.coroutines.delay

// This composable is use to create splash screen that user will see when the app is first loaded

@Composable
fun AnimatedSplashScreen(
    onLoadAuth: () -> Unit,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim:Float by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        onLoadAuth()
    }
    Splash(alpha = alphaAnim)
}


@Composable
fun Splash(alpha: Float) {
    Column(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) dark_primaryContainer else light_primaryContainer)
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_icon),
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha)
                .weight(2f),
            contentDescription = "Logo Icon",
            tint = if (isSystemInDarkTheme()) dark_onPrimaryContainer else light_onPrimaryContainer
        )
        Text(
            text = stringResource(id = R.string.developer_name),
            color = if (isSystemInDarkTheme()) dark_onPrimaryContainer else light_onPrimaryContainer
        )
    }
}


@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(alpha = 1f)
}