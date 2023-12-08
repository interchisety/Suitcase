package com.example.suitcase.ui.presentation.screens.onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.suitcase.ui.theme.SuitCaseTheme
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_onPrimaryContainer
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.light_primaryContainer
import com.google.accompanist.pager.ExperimentalPagerApi
import com.example.suitcase.ui.theme.light_secondary
import com.example.suitcase.ui.theme.light_surfaceTint
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val pages = listOf(
        OnBoardingSlider.First,
        OnBoardingSlider.Second,
    )

    val pagerState = rememberPagerState(pageCount = pages.size)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 26.dp, end = 26.dp, top = 100.dp, bottom = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
        ) { position ->
            PagerScreen(onBoardingPages = pages[position])
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = light_surfaceTint,
            inactiveColor = light_secondary
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp),
            verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = {
                    /*Sign up Screen*/
                    onSignUpClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) light_primary else light_primary ,
                    contentColor = if (isSystemInDarkTheme()) light_onPrimary else light_onPrimary
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Get Started", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    /*Login Screen*/
                    onLoginClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),//dark_primary
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) light_primaryContainer else light_primaryContainer,
                    contentColor = if (isSystemInDarkTheme()) light_onPrimaryContainer else light_onPrimaryContainer
                ),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Log in", style = MaterialTheme.typography.titleMedium)
            }
        }


    }
}

@Composable
fun PagerScreen(onBoardingPages: OnBoardingSlider) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            painter = painterResource(id = onBoardingPages.image),
            contentDescription = "Pager Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPages.description,
            style = MaterialTheme.typography.titleMedium,
            color = light_secondary,
            textAlign = TextAlign.Center,
        )
    }
}


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    SuitCaseTheme(useDarkTheme = true) {
        OnBoardingScreen(
            onSignUpClick = {} ,
            onLoginClick = { }
        )
    }
}