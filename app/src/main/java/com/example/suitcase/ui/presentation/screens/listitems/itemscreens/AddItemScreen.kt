package com.example.suitcase.ui.presentation.screens.listitems.itemscreens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.navigation.AddItemRoute
import com.example.suitcase.ui.theme.dark_onPrimaryContainer
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.secondaryDarkBlue

@Composable
fun AddItemScreen(
    onBackClick:()->Unit
) {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
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
                    text = "Add Item",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = dark_onSecondary,
                )
                Text(text = "")

            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )
        {
            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                )
                {
                    TextField(
                        value = "",
                        onValueChange = {
                            //onEvent(UserListEvent.SetTitle(it))
                        },
                        label = {
                            Text(text = "Name")
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .background(secondaryDarkBlue)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = dark_onPrimaryContainer,
                            focusedContainerColor = dark_onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {
                            //onEvent(UserListEvent.SetTitle(it))
                        },
                        label = {
                            Text(text = "Price")
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .background(secondaryDarkBlue)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = dark_onPrimaryContainer,
                            focusedContainerColor = dark_onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {
                            //onEvent(UserListEvent.SetTitle(it))
                        },
                        label = {
                            Text(text = "Quantity")
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .background(secondaryDarkBlue)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = dark_onPrimaryContainer,
                            focusedContainerColor = dark_onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {
                            //onEvent(UserListEvent.SetDesc(it))
                        },
                        label = {
                            Text(text = "Description")
                        },
                        modifier = Modifier
                            .height(54.dp)
                            .background(secondaryDarkBlue)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = dark_onPrimaryContainer,
                            focusedContainerColor = dark_onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { //onAddItemClick()
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Photo",
                            tint = light_primary
                        )
                        Text(
                            text = "Add Photo",
                            style = MaterialTheme.typography.bodyLarge,
                            color = light_primary,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (selectedImageUri != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                                .height(200.dp)
                        ){
                            item {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            item {
                                Row(modifier = Modifier.fillMaxWidth().clickable {
                                        selectedImageUri = null
                                    },
                                    horizontalArrangement = Arrangement.Center){
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Add Photo",
                                        tint = light_primary
                                    )
                                    Text(
                                        text = "Remove Photo",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = light_primary,
                                    )
                                }
                            }
                        }
                    }
                    Row(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable { //onAddItemClick()

                        },
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add Photo",
                            tint = light_primary
                        )
                        Text(
                            text = "Add Location",
                            style = MaterialTheme.typography.bodyLarge,
                            color = light_primary,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            // onEvent(UserListEvent.SaveUserList)
                            //onEvent(UserListEvent.HideDialog)
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
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddItemScreenPreview() {
    AddItemScreen(
        onBackClick = {}
    )
}



fun NavGraphBuilder.addItemRoute(navController: NavHostController) {
    composable(
        route = AddItemRoute.route,
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
        AddItemScreen(onBackClick = { navController.navigateUp() })
    }
}