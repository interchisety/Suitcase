package com.example.suitcase.ui.presentation.screens.authentication




import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.screens.authentication.component.InputComponent
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.light_secondary
import com.example.suitcase.ui.presentation.screens.authentication.viewmodel.SignUpEvent
import com.example.suitcase.ui.presentation.screens.authentication.viewmodel.SignupViewModel
import com.example.suitcase.util.ConnectionState
import com.example.suitcase.util.connectivityState
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SignUpScreen(
    signUpVM: SignupViewModel = viewModel(),
    onGoBackClick: () -> Unit,
    onRegisterclick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val connection by connectivityState()

    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val uiState by signUpVM.state.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        onGoBackClick()
                    },
                    painter = painterResource(id = R.drawable.round_arrow_back_24),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(26.dp))

                Column {
                    Text(
                        text = "Create Account",
                        color = dark_onSecondary,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Create an account to start add your items for next trip",
                        color = light_secondary
                    )
                }
                Spacer(modifier = Modifier.height(26.dp))



                Column {
                    InputComponent(
                        value = uiState.fullname,
                        onValueChange = { signUpVM.onEvent(SignUpEvent.SetFullname(it))},
                        label = "Full name",
                        leadingIconImageVector = Icons.Default.Person,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    InputComponent(
                        value = uiState.email,
                        onValueChange = { signUpVM.onEvent(SignUpEvent.SetEmail(it)) },
                        label = "Email",
                        leadingIconImageVector = Icons.Default.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputComponent(
                        value = uiState.password,
                        onValueChange = { signUpVM.onEvent(SignUpEvent.SetPassword(it)) },
                        label = "Password",
                        isPasswordField = true,
                        isPasswordVisible = uiState.isPasswordVisible,
                        onVisibilityChange = { signUpVM.onEvent(SignUpEvent.PasswordVisibility) },
                        leadingIconImageVector = ImageVector.vectorResource(id = R.drawable.baseline_lock_24),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Button(
                    enabled = uiState.isFormFilled,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    onClick = {
                        if (connection == ConnectionState.Available){
                            signUpVM.onEvent(SignUpEvent.Register)

                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = uiState.userSignInInfor,
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                                when (result) {
                                    SnackbarResult.Dismissed -> {

                                    }

                                    SnackbarResult.ActionPerformed -> {

                                    }
                                }
                            }
                            onRegisterclick()
                            signUpVM.onEvent(SignUpEvent.Reset)

                        }
                        else {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Check your data network",
                                    actionLabel = "Retry",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Short
                                )
                                when (result) {
                                    SnackbarResult.Dismissed -> {

                                    }

                                    SnackbarResult.ActionPerformed -> {

                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dark_primaryContainer,
                        contentColor = light_onPrimary
                    )
                ) {
                    Text(text = "Create account", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account?",
                        color = light_secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Log in",
                        color = light_primary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable {
                            onLoginClick()
                        })
                }
            }
        }

    }
}



@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onGoBackClick={},
        onRegisterclick = {} ,
        onLoginClick = {}
    )
}

