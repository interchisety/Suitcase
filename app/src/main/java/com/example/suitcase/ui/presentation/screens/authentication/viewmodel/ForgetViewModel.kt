package com.example.suitcase.ui.presentation.screens.authentication.viewmodel


import android.util.Log
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

class ForgetViewModel(
    private val repository: Repository = AppContainer.repository
) : ViewModel() {
    private val _forgetState = MutableStateFlow(ForgetState())
    val state = _forgetState.asStateFlow()

    private val TAG = SignupViewModel::class.simpleName

    // event that responds to user actions on the signup screen
    fun onEvent(event: ForgetEvent) {
        when (event) {
            is ForgetEvent.SetEmail -> {
                _forgetState.update { it.copy(email = event.email) }
            }
            is ForgetEvent.SetRePass -> {
                _forgetState.update { it.copy(repass = event.repass) }
            }
            is ForgetEvent.SetPassword -> {
                _forgetState.update { it.copy(password = event.password) }
            }
            is ForgetEvent.PasswordVisibility -> {
                _forgetState.update { it.copy(isPasswordVisible = !_forgetState.value.isPasswordVisible) }
            }
            is ForgetEvent.RePasswordVisibility -> {
                _forgetState.update { it.copy(isRePasswordVisible = !_forgetState.value.isRePasswordVisible) }
            }
            is ForgetEvent.Register -> {

                if (_forgetState.value.validationCompleted){
                    viewModelScope.launch {
                        signup()
                        /*if(repository.signUp(_signState.value.email,_signState.value.password)){
                            _signState.update { it.copy(
                                userSignInInfor = "Successful"
                            ) }
                        }*/
                    }
                }else{
                    Log.d(TAG, "Showing validation errror  check")
                    showValidationError()
                }
            }

            ForgetEvent.Reset -> {
                _forgetState.update { it.copy(
                    repass = "",
                    email = "",
                    password = "",
                    isPasswordVisible = false,
                    isFormFilled = false,
                    isComfirmed = false,
                    userSignInInfor = "",
                    fullnameError = false,
                    emailError = false,
                    passwordError = false
                ) }
            }

            else -> {}
        }
    }

    private fun updateCompleteValidation() {
        if (_forgetState.value.fullnameError && _forgetState.value.emailError
            && _forgetState.value.passwordError) _forgetState.update { it.copy(
            validationCompleted = true
        ) }
        Log.d(TAG, "Inside updateCompleteValidation function and the value is now ${_forgetState.value.validationCompleted}")
    }

    private fun showValidationError() {
        if(!_forgetState.value.emailError && !_forgetState.value.passwordError){
            _forgetState.update { it.copy(
                userSignInInfor = "Invalid email or password"
            ) }
        }
        if( _forgetState.value.emailError && !_forgetState.value.passwordError){
            _forgetState.update { it.copy(
                userSignInInfor = "Password must contains numbers,numbers and "
            ) }
        }
        if( !_forgetState.value.emailError && _forgetState.value.passwordError){
            _forgetState.update { it.copy(
                userSignInInfor = "Invalid email format "
            ) }
        }
    }

    private fun signup() {
        // signup user collected data on Firebase
        createAccount(_forgetState.value.email, _forgetState.value.password)
    }

    private fun createAccount(email: String, password: String) {
        try {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "Inside_OnCompleteListener")
                    Log.d(TAG, "isSuccessful = ${task.isSuccessful}")
                    if (task.isSuccessful) {
                        _forgetState.update { it.copy(
                            isComfirmed = true,
                            userSignInInfor = "Successful"
                        ) }
                    } else {
                        // Handle unsuccessful signup
                        _forgetState.update { it.copy(
                            isComfirmed = false,
                            userSignInInfor = "Failed"
                        ) }
                        Log.d(TAG, "Signup failed: ${task.exception?.message}")
                        handleException(task.exception ?: Exception("Unknown error"))
                    }
                }
        } catch (e: Exception) {
            // Handle the exception, e.g., show an error message to the user
            handleException(e)
        }
    }

    private fun userFormFilled() {
        _forgetState.update {
            it.copy(
                isFormFilled = !_forgetState.value.repass.isNullOrEmpty()
                        && !_forgetState.value.email.isNullOrEmpty()
                        && !_forgetState.value.password.isNullOrEmpty()
            )
        }
    }

    fun validateUserInputResult() {
        val fullnameValidateResult = Validator.validateFullname(_forgetState.value.repass)
        val emailValidateResult = Validator.validateEmail(_forgetState.value.email)
        val passwordValidateResult = Validator.validatePassword(_forgetState.value.password)

        // assign all inputed error messages
        _forgetState.update {
            it.copy(
                fullnameError = fullnameValidateResult.status,
                emailError = emailValidateResult.status,
                passwordError = passwordValidateResult.status
            )
        }
        Log.d(TAG, "Running inside validateresult right nowr")
    }

    private fun handleException(exception: Exception) {
        // Implement your error handling logic here, e.g., logging or showing an error message to the user
        Log.e(TAG, "Error: ${exception.message}", exception)
        // You can also use a LiveData to communicate the error to the UI if needed
        // _errorLiveData.value = "An error occurred: ${exception.message}"
    }
}



//state manager for the signup screen
data class ForgetState(
    val email:String = "",
    val password: String = "",
    val repass:String = "",
    val isPasswordVisible:Boolean = false,
    val isRePasswordVisible:Boolean = false,
    val isFormFilled:Boolean = false,
    val isComfirmed:Boolean = false,
    val userSignInInfor:String = "",

    val fullnameError:Boolean = false,
    val emailError:Boolean = false,
    val passwordError:Boolean = false,

    val validationCompleted:Boolean = false
)

//event data that respond to user action that are perform at the signup screen
sealed interface ForgetEvent{
    data class SetRePass(val repass:String): ForgetEvent
    data class SetEmail(val email:String): ForgetEvent
    data class SetPassword(val password: String): ForgetEvent
    object PasswordVisibility: ForgetEvent
    object RePasswordVisibility: ForgetEvent
    object Register: ForgetEvent
    object Reset: ForgetEvent
}