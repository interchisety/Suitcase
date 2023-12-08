package com.example.suitcase.ui.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.suitcase.BottomBarScreen
import com.example.suitcase.R
import com.example.suitcase.ui.theme.dark_onPrimaryContainer
import com.example.suitcase.ui.theme.light_onPrimaryContainer
import com.example.suitcase.ui.theme.light_onSurface
import com.example.suitcase.ui.theme.primaryDarkBlue
import com.example.suitcase.ui.theme.secondaryDarkBlue
import com.example.suitcase.ui.theme.secondaryLightBlue
import com.example.suitcase.ui.viewmodal.UserListEvent
import com.example.suitcase.ui.viewmodal.UserListState


@Composable
fun BottomAppBarComponent(navController: NavHostController) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Itemlist
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach{ item ->
            AddItem(
                screen = item,
                navController = navController,
                currentRoute = currentRoute)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen:BottomBarScreen,
    navController: NavHostController,
    currentRoute:String?) {
    NavigationBarItem(
        label ={
               Text(text = screen.title)
        },
        icon ={
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.title
            )
        },
        selected = currentRoute == screen.route,
        alwaysShowLabel = true,
        onClick = {
                  navController.navigate(screen.title){
                      popUpTo(navController.graph.findStartDestination().id){
                          saveState = true
                      }
                      launchSingleTop = true
                      restoreState = true
                  }
        },
        colors = NavigationBarItemDefaults.colors()
    )
}

@Composable
fun ListItemComponent(listObj: ListItem, bgColor:Color) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(100.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor
        )

    ) {
            Column{
                Text(
                    text=listObj.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text=listObj.desc,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text=listObj.createdDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
    }
}

@Composable
fun CreateListDialog(
    state: UserListState,
    onEvent:(UserListEvent) -> Unit
) {
    Dialog(
        onDismissRequest = {
              onEvent(UserListEvent.HideDialog)
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
                verticalArrangement = Arrangement.Center)
            {
                Text(
                    text = "Create list",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(UserListEvent.SetTitle(it))
                    },
                    label = {
                        Text(
                            text = "List title",
                            color = primaryDarkBlue
                        )
                    },
                    modifier = Modifier
                        .height(54.dp)
                        .background(secondaryDarkBlue)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = secondaryLightBlue,
                        focusedContainerColor = secondaryLightBlue
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.desc,
                    onValueChange = {
                        onEvent(UserListEvent.SetDesc(it))
                    },
                    label = {
                        Text(
                            text = "Description",
                            color = primaryDarkBlue
                        )
                    },
                    modifier = Modifier
                        .height(54.dp)
                        .background(secondaryDarkBlue)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = secondaryLightBlue,
                        focusedContainerColor = secondaryLightBlue
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    onEvent(UserListEvent.SaveUserList)
                      //onEvent(UserListEvent.HideDialog)
                },
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),) {
                    Text(text = "Create")
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MyPreview2(){
    BottomAppBarComponent(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun MyPreview3(){
    val _listIlem = ListItem(
        name = "Car",
        desc = "Getting car for my wedding anniversary",
        createdDate = "11/12/2024"
    )
    ListItemComponent(_listIlem, primaryDarkBlue)
}

data class ListItem(
    val name: String,
    val desc: String,
    val createdDate: String
)