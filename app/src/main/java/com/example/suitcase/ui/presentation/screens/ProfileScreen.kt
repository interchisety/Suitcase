package com.example.suitcase.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.suitcase.ui.presentation.navigation.ListRoute
import com.example.suitcase.ui.viewmodal.UserListEvent
import com.example.suitcase.ui.viewmodal.UserListState
import com.example.suitcase.ui.viewmodal.UserListViewModel

@Composable
fun ProfileScreen(
    state: UserListState,
    onEvent: (UserListEvent) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.userlists.size) { index  ->
            UserListComponent(
                state = state,
                index = index,
                onEvent = onEvent
            )
        }
    }
}


@Composable
fun UserListComponent(
    state: UserListState,
    index: Int,
    onEvent: (UserListEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = state.userlists[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = state.userlists[index].desc,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }

        IconButton(
            onClick = {
                onEvent(UserListEvent.DeleteList(state.userlists[index]))
            }
        ) {

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Note",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

        }

    }
}


fun NavGraphBuilder.addProfileRoute() {
    composable(
        route = ListRoute.route,
    ) {
        val viewmodel = viewModel<UserListViewModel>()
        val state by viewmodel.state.collectAsState()
        ProfileScreen(state = state, onEvent = viewmodel::onEvent )
    }
}