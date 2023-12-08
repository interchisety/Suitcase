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

class SignupViewModel(
    private val repository: Repository = AppContainer.repository
) : ViewModel() {
    private val _signState = MutableStateFlow(SignupState())
    val state = _signState.asStateFlow()

    private val TAG = SignupViewModel::class.simpleName

    // event that responds to user actions on the signup screen
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SetEmail -> {
                _signState.update { it.copy(email = event.email) }
            }
            is SignUpEvent.SetFullname -> {
                _signState.update { it.copy(fullname = event.fullname) }
            }
            is SignUpEvent.SetPassword -> {
                _signState.update { it.copy(password = event.password) }
            }
            is SignUpEvent.PasswordVisibility -> {
                _signState.update { it.copy(isPasswordVisible = !_signState.value.isPasswordVisible) }
            }
            is SignUpEvent.Register -> {
                if (_signState.value.fullnameError && _signState.value.emailError
                    && _signState.value.passwordError){
                    signup()
                }
                else{
                    showValidationError()
                }
            }

            SignUpEvent.Reset -> {
                _signState.update { it.copy(
                    fullname = "",
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

        validateUserInputResult()
        userFormFilled()
    }

    private fun validateUserInputResult() {
        val fullnameValidateResult = Validator.validateFullname(_signState.value.fullname)
        val emailValidateResult = Validator.validateEmail(_signState.value.email)
        val passwordValidateResult = Validator.validatePassword(_signState.value.password)

        // assign all inputed error messages
        _signState.update {
            it.copy(
                fullnameError = fullnameValidateResult.status,
                emailError = emailValidateResult.status,
                passwordError = passwordValidateResult.status
            )
        }
    }

    private fun userFormFilled() {
        _signState.update {
            it.copy(
                isFormFilled = !_signState.value.fullname.isNullOrEmpty()
                        && !_signState.value.email.isNullOrEmpty()
                        && !_signState.value.password.isNullOrEmpty()
            )
        }
    }


    private fun showValidationError() {
        if(!_signState.value.emailError && !_signState.value.passwordError){
            _signState.update { it.copy(
                userSignInInfor = "Invalid email or password"
            ) }
        }
        if( _signState.value.emailError && !_signState.value.passwordError){
            _signState.update { it.copy(
                userSignInInfor = "Password must contains numbers,numbers and "
            ) }
        }
        if( !_signState.value.emailError && _signState.value.passwordError){
            _signState.update { it.copy(
                userSignInInfor = "Invalid email format "
            ) }
        }
    }

    private fun signup() {
            // signup user collected data on Firebase
            createAccount(_signState.value.email, _signState.value.password)
    }

    private fun createAccount(email: String, password: String) {
        try {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "Inside_OnCompleteListener")
                    Log.d(TAG, "isSuccessful = ${task.isSuccessful}")
                    if (task.isSuccessful) {
                        _signState.update { it.copy(
                            isComfirmed = true,
                            userSignInInfor = "Successful"
                        ) }
                    } else {
                        // Handle unsuccessful signup
                        _signState.update { it.copy(
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





    private fun handleException(exception: Exception) {
        // Implement your error handling logic here, e.g., logging or showing an error message to the user
        Log.e(TAG, "Error: ${exception.message}", exception)
        // You can also use a LiveData to communicate the error to the UI if needed
        // _errorLiveData.value = "An error occurred: ${exception.message}"
    }
}



//state manager for the signup screen
data class SignupState(
    val fullname:String = "",
    val email:String = "",
    val password: String = "",

    val fullnameError:Boolean = false,
    val emailError:Boolean = false,
    val passwordError:Boolean = false,

    val isPasswordVisible:Boolean = false,

    val isFormFilled:Boolean = false,
    val isComfirmed:Boolean = false,
    val userSignInInfor:String = "",
)

//event data that respond to user action that are perform at the signup screen
sealed interface SignUpEvent{
    data class SetFullname(val fullname:String): SignUpEvent
    data class SetEmail(val email:String): SignUpEvent
    data class SetPassword(val password: String): SignUpEvent
    object PasswordVisibility: SignUpEvent
    object Register: SignUpEvent
    object Reset: SignUpEvent
}