package com.example.suitcase.ui.presentation.screens.listitems.listscreens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.navigation.AddItemRoute
import com.example.suitcase.ui.presentation.navigation.AddListRoute
import com.example.suitcase.ui.presentation.navigation.NewListDetailsRoute
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.light_secondaryContainer
import com.example.suitcase.ui.viewmodal.NewListState
import com.example.suitcase.ui.viewmodal.NewListViewModal
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddNewListScreen(
    state: NewListState,
    onBackClick: () -> Unit,
    onAddItemClick: () -> Unit,
    onButtonClick:() -> Unit
) {
    val listState = rememberLazyListState()
    val isExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    Scaffold(
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
                        onBackClick()
                    },
                    painter = painterResource(id = R.drawable.round_arrow_back_24),
                    contentDescription = null
                )
                Text(
                    text = "Create List",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = dark_onSecondary,
                )
                Text(text = "")

            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = dark_primaryContainer,
                contentColor = light_onPrimary,
                onClick = { /* do something*/ },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
                text = { Text(text = "Create")},
                expanded = isExpanded
            )
        },
        floatingActionButtonPosition = FabPosition.End

    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )
        {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .background(light_secondaryContainer)
                        .clickable { onButtonClick() }
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                    ) {
                        Row(modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "#2",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = state.createdate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy",
                                Locale.ENGLISH)),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End) {
                            Text(text = "10 Items",style = MaterialTheme.typography.bodyMedium,)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                                contentDescription = null,
                                tint = light_primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { onAddItemClick() }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Item",
                        tint = light_primary
                    )
                    Text(
                        text = "Add Item",
                        style = MaterialTheme.typography.bodyLarge,
                        color = light_primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddListListScreenPreview() {
    val viewmodel = viewModel<NewListViewModal>()
    val state by viewmodel.state.collectAsState()
    AddNewListScreen(
        state = state,
        onBackClick = {},
        onButtonClick = {},
        onAddItemClick = {}
    )
}


fun NavGraphBuilder.addNewListRoute(
    viewmodel:NewListViewModal,
    baseNavController: NavHostController,
    navController: NavHostController
) {
    composable(
        route = AddListRoute.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
       // val viewmodel = viewModel<NewListViewModal>()
        val state by viewmodel.state.collectAsState()
        AddNewListScreen(
            state = state,
            onAddItemClick = {
                navController.navigate(
                    route=AddItemRoute.route
                )
            },
            onBackClick = {
                baseNavController.navigateUp()
            },
            onButtonClick = {
                navController.navigate(
                    route = NewListDetailsRoute.route
                )
            })
    }
}