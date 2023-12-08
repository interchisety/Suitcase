package com.example.suitcase.ui.presentation.screens.authentication.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitcase.AppContainer
import com.example.suitcase.ui.repository.Repository
import com.example.suitcase.ui.validator.Validator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository = AppContainer.repository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val state = _loginState.asStateFlow()

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    private val TAG = SignupViewModel::class.simpleName

    // event that responds to user actions on the signup screen
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SetEmail -> {
                _loginState.update { it.copy(email = event.email) }
            }
            is LoginEvent.SetPassword -> {
                _loginState.update { it.copy(password = event.password) }
            }
            is LoginEvent.PasswordVisibility -> {
                _loginState.update { it.copy(isPasswordVisible = !_loginState.value.isPasswordVisible) }
            }
            is LoginEvent.Login -> {

                if (allValidationsPassed.value){
                    viewModelScope.launch {
                        login()
                    }
                }else{
                    Log.d(TAG, "Showing validation errror  check")
                    showValidationError()
                }
            }

            LoginEvent.Reset -> {
                _loginState.update { it.copy(
                    email = "",
                    password = "",
                    isPasswordVisible = false,
                    isFormFilled = false,
                    userSignInInfor = "",
                    emailError = false,
                    passwordError = false
                ) }
            }

            else -> {}
        }
        validateLoginInputResult()
        userFormFilled()
    }

    private fun validateLoginInputResult() {
        val emailResult = Validator.validateEmail(
            email = _loginState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = _loginState.value.password
        )
        _loginState.value = _loginState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun login() {
        loginInProgress.value = true
        val email = _loginState.value.email
        val password = _loginState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_login_success")
                Log.d(TAG,"${it.isSuccessful}")

                if(it.isSuccessful){
                    _loginState.update { it.copy(
                        userSignInInfor = "Login successful"
                    ) }
                    loginInProgress.value = false
                    //PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")
                val error:String = it.localizedMessage
                _loginState.update { it.copy(
                    userSignInInfor = error
                ) }
                loginInProgress.value = false

            }

    }

    private fun showValidationError() {
        if(!_loginState.value.emailError && !_loginState.value.passwordError){
            _loginState.update { it.copy(
                userSignInInfor = "Invalid email or password"
            ) }
        }
        if( _loginState.value.emailError && !_loginState.value.passwordError){
            _loginState.update { it.copy(
                userSignInInfor = "Password must contains numbers,numbers and "
            ) }
        }
        if( !_loginState.value.emailError && _loginState.value.passwordError){
            _loginState.update { it.copy(
                userSignInInfor = "Invalid email format "
            ) }
        }
    }


    private fun userFormFilled() {
        _loginState.update {
            it.copy(
                isFormFilled = !_loginState.value.email.isNullOrEmpty()
                        && !_loginState.value.password.isNullOrEmpty()
            )
        }
    }
}



//state manager for the signup screen
data class LoginState(
    val email:String = "",
    val password: String = "",
    val isPasswordVisible:Boolean = false,
    val isFormFilled:Boolean = false,

    val userSignInInfor:String = "",

    val emailError:Boolean = false,
    val passwordError:Boolean = false,

    val validationCompleted:Boolean = false
)

//event data that respond to user action that are perform at the signup screen
sealed interface LoginEvent{
    data class SetEmail(val email:String): LoginEvent
    data class SetPassword(val password: String): LoginEvent
    object PasswordVisibility: LoginEvent
    object Login: LoginEvent
    object Reset: LoginEvent
}