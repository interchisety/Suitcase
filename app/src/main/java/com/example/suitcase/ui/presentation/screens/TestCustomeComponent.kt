package com.example.suitcase.ui.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.suitcase.R
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.light_onSurfaceVariant
import com.example.suitcase.ui.theme.light_secondaryContainer

@Composable
fun NewListCompose(
    onGoBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar =
            {
                 Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(vertical = 16.dp, horizontal = 16.dp),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.SpaceBetween
                 ) {
                     //Back Arrow
                     Icon(
                         modifier = Modifier.clickable {
                             onGoBackClick()
                         },
                         painter = painterResource(id = R.drawable.round_arrow_back_24),
                         contentDescription = null
                     )
                     Text(
                         text = "New List",
                         style = MaterialTheme.typography.headlineSmall,
                         fontWeight = FontWeight.Bold,
                         color = dark_onSecondary,
                     )
                     Text(text = "")

                 }
        }
    ) {padding ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(light_secondaryContainer)
                .clickable { },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription =null,
                tint = light_onSurfaceVariant)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewListComposePreview() {
    val navController = rememberNavController()
    NewListCompose (
        onGoBackClick = {
            navController.popBackStack()
        }
    )
}