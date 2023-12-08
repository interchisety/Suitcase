package com.example.suitcase.ui.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.navigation.NewListDetailsRoute
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_outline
import com.example.suitcase.ui.viewmodal.NewListEvent
import com.example.suitcase.ui.viewmodal.NewListState
import com.example.suitcase.ui.viewmodal.NewListViewModal
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NewListDetailsScreen(
    state: NewListState,
    onEvent:(NewListEvent) -> Unit,
    onBackClick:()->Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
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
                    text = "Details",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = dark_onSecondary,
                )
                Text(text = "")

            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )
        {
            Column(modifier = Modifier.fillMaxWidth())
            {
                Column{
                    Divider(
                        color = light_outline,
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Date",
                        style = MaterialTheme.typography.titleMedium,
                        color = dark_onSecondary
                    )
                    BasicTextField(
                        value = state.createdate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy",
                            Locale.ENGLISH)),
                        onValueChange = {
                            //
                        },
                        readOnly = true,
                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                }

                Column{
                    Divider(
                        color = light_outline,
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Name",
                        style = MaterialTheme.typography.titleMedium,
                        color = dark_onSecondary
                    )
                    BasicTextField(
                        value = state.listname,
                        onValueChange = {
                            onEvent(NewListEvent.SetListName(it))
                        },
                        keyboardActions = KeyboardActions.Default,
                        keyboardOptions = KeyboardOptions.Default,
                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                }

                Column{
                    Divider(
                        color = light_outline,
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        color = dark_onSecondary
                    )
                    BasicTextField(
                        value = state.desc,
                        onValueChange = {
                            onEvent(NewListEvent.SetDesc(it))
                        },
                        keyboardActions = KeyboardActions.Default,
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = false,
                        maxLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                }


                Column(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                    Button(
                        onClick = {
                            scope.launch {
                                if (state.listname.isBlank() || state.desc.isBlank()){
                                    val result = snackbarHostState.showSnackbar(
                                        message = "List name and description can't be empty",
                                        actionLabel = "Retry",
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Long
                                    )
                                    when (result) {
                                        SnackbarResult.Dismissed -> {

                                        }

                                        SnackbarResult.ActionPerformed -> {
                                            /* viewModel.sendPhoto*/
                                        }
                                    }
                                } else{
                                    onBackClick()
                                }
                            }
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = dark_primaryContainer,
                            contentColor = light_onPrimary
                        )
                    ) {
                        Text( text = "Update")
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun NewListDetailsScreenPreview() {
    val viewmodel = viewModel<NewListViewModal>()
    val state by viewmodel.state.collectAsState()
    NewListDetailsScreen(
        state = state,
        onEvent = viewmodel::onEvent
    ) { }
}


fun NavGraphBuilder.listDetailsRoute(
    viewmodel: NewListViewModal,
    navController: NavHostController)
{
    composable(
        route = NewListDetailsRoute.route,
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
        //val viewmodel = viewModel<NewListViewModal>()
        val state by viewmodel.state.collectAsState()
        NewListDetailsScreen(
            state = state,
            onEvent = viewmodel::onEvent
        ) {
            navController.navigateUp()
        }
    }
}