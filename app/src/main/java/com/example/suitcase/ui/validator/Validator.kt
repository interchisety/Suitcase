package com.example.suitcase.ui.validator

import android.util.Patterns

object Validator {
    fun validateFullname(fullname:String):ValidationResult{
        return ValidationResult(
            !fullname.isNullOrEmpty()
        )
    }
    fun validateEmail(email:String):ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        )
    }
    fun validatePassword(password:String):ValidationResult{
        val passwordRegx = "(?=.*\\d)(?=.*[a-z])(?=.*[@#\$%^&+=]).{8,}".toRegex()
        return ValidationResult(
            (!password.isNullOrEmpty() && passwordRegx.matches(password))
        )
    }
}

data class ValidationResult(
    val status:Boolean = false
)