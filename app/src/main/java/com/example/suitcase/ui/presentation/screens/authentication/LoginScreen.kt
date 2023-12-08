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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suitcase.R
import com.example.suitcase.ui.presentation.screens.authentication.component.InputComponent
import com.example.suitcase.ui.presentation.screens.authentication.viewmodel.LoginEvent
import com.example.suitcase.ui.presentation.screens.authentication.viewmodel.LoginViewModel
import com.example.suitcase.ui.presentation.screens.authentication.viewmodel.SignUpEvent
import com.example.suitcase.ui.theme.dark_onSecondary
import com.example.suitcase.ui.theme.dark_primaryContainer
import com.example.suitcase.ui.theme.light_onPrimary
import com.example.suitcase.ui.theme.light_onSecondaryContainer
import com.example.suitcase.ui.theme.light_primary
import com.example.suitcase.ui.theme.light_secondary
import com.example.suitcase.util.ConnectionState
import com.example.suitcase.util.connectivityState
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun LoginScreen(
    logInVM: LoginViewModel = viewModel(),
    onGoBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotClick:()->Unit
) {
    val connection by connectivityState()

    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val uiState by logInVM.state.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {
                //Back Arrow
                Icon(
                    modifier = Modifier.clickable {
                        onGoBackClick()
                    },
                    painter = painterResource(id = R.drawable.round_arrow_back_24),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(26.dp))

                //Login design detail
                Column {
                    Text(
                        text = stringResource(id = R.string.welcome),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = dark_onSecondary,
                    )
                    Text(
                        text = stringResource(id = R.string.signin),
                        color = light_secondary
                    )
                    Spacer(modifier = Modifier.height(26.dp))

                    Column {
                        InputComponent(
                            value = uiState.email,
                            onValueChange = { logInVM.onEvent(LoginEvent.SetEmail(it)) },
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
                            onValueChange = { logInVM.onEvent(LoginEvent.SetPassword(it)) },
                            label = "Password",
                            isPasswordField = true,
                            isPasswordVisible = uiState.isPasswordVisible,
                            onVisibilityChange = { logInVM.onEvent(LoginEvent.PasswordVisibility) },
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                        ){
                        Text(
                            text = "forget password",
                            color = light_onSecondaryContainer,
                            modifier = Modifier
                                .clickable {
                                    onForgotClick()
                                },
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))
                    Button(
                        enabled = logInVM.allValidationsPassed.value,
                        onClick = {
                            scope.launch {
                                if (connection == ConnectionState.Available){
                                    scope.launch {
                                        logInVM.onEvent(LoginEvent.Login)
                                    }.join()
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
                                    logInVM.onEvent(LoginEvent.Reset)
                                    onLoginClick()
                                }
                                else{
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
                        Text(text = "Log in", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't you have an account?",
                            color = light_secondary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Sign up",
                            color = light_primary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.clickable {
                                onSignUpClick()
                            })
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onGoBackClick={},
        onLoginClick = {},
        onSignUpClick = {} ,
        onForgotClick = {}
    )
}