package com.example.suitcase.ui.presentation.screens.listitems.homescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.suitcase.R
import com.example.suitcase.ui.theme.primaryDarkBlue
import com.example.suitcase.ui.theme.primaryLightBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListTopAppBarComponent(username: String,time:String){
    TopAppBar(
        modifier = Modifier.padding(vertical = 6.dp),
        title = {
            Row(modifier = Modifier
                .fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier
                        .size(61.dp)
                        .aspectRatio(1f, matchHeightConstraintsFirst = true)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                        .padding(1.dp)
                        .clip(CircleShape)

                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.headlineMedium,
                        color = primaryDarkBlue
                    )
                    Text(
                        text = time,
                        color =  primaryLightBlue
                    )
                }

            }
        })
}