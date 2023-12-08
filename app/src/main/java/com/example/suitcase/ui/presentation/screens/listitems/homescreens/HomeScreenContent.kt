package com.example.suitcase.ui.presentation.screens.listitems.homescreens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.suitcase.ui.presentation.components.CreateListDialog
import com.example.suitcase.ui.presentation.components.ListItem
import com.example.suitcase.ui.presentation.navigation.AddListRoute
import com.example.suitcase.ui.presentation.navigation.HomeRoute
import com.example.suitcase.ui.viewmodal.UserListEvent
import com.example.suitcase.ui.viewmodal.UserListState
import com.example.suitcase.ui.viewmodal.UserListViewModel


@Composable
fun HomeScreenContent(
    navController: NavController,
    state: UserListState,
    onEvent:(UserListEvent) -> Unit
) {
    var username by remember {
        mutableStateOf("Olaniyi")
    }
    var time by remember {
        mutableStateOf("Good Morning")
    }
    var search by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = { HomeListTopAppBarComponent(username = username, time = time) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        //onEvent(UserListEvent.ShowDialog)
                    navController.navigate(AddListRoute.route)
                }) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
    ) { paddingValues ->
        if(state.isAddingList) {
            CreateListDialog(state = state, onEvent = onEvent)
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            val _listIlem = ListItem(
                name = "Car",
                desc = "Getting car for my wedding anniversary",
                createdDate = "11/12/2024"
            )
            //ListItemComponent(_listIlem, primaryDarkBlue)
        }

    }
}



fun NavGraphBuilder.addHomeRoute(baseNavController: NavHostController) {
    composable(route = HomeRoute.route){
        val viewmodel = viewModel<UserListViewModel>()
        val state by viewmodel.state.collectAsState()
        HomeScreenContent(navController= baseNavController,state = state, onEvent = viewmodel::onEvent  )
    }
}
