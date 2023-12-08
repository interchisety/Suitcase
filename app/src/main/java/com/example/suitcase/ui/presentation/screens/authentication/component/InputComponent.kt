package com.example.suitcase.ui.presentation.screens.authentication.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.suitcase.R
import com.example.suitcase.ui.theme.dark_onPrimaryContainer
import com.example.suitcase.ui.theme.light_onSurface
import com.example.suitcase.ui.theme.light_secondary

@Composable
fun InputComponent(
    value: String,
    onValueChange:(String) -> Unit,
    label:String = "",
    leadingIconImageVector: ImageVector,
    leadingIconDescription:String = "",
    isPasswordField:Boolean=false,
    isPasswordVisible:Boolean=false,
    onVisibilityChange:(Boolean)->Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = value,
            onValueChange = {onValueChange(it)},
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = dark_onPrimaryContainer,
                unfocusedTextColor = dark_onPrimaryContainer,
                unfocusedContainerColor = dark_onPrimaryContainer,
                focusedContainerColor = dark_onPrimaryContainer
            ),
            textStyle = TextStyle(
                color = if (isSystemInDarkTheme()) light_onSurface else light_onSurface
            ),
            label = { Text(
                label,
                color = light_secondary
            )},
            leadingIcon = {
                Icon(
                    imageVector = leadingIconImageVector,
                    contentDescription = leadingIconDescription,
                    tint = if (isSystemInDarkTheme()) light_onSurface else light_onSurface
                )
            },
            trailingIcon = {
                if (isPasswordField){
                    IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                        Icon(
                            painter = if (isPasswordVisible){
                                painterResource(id = R.drawable.baseline_visibility_24)
                            } else painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = null,
                            tint = if (isSystemInDarkTheme()) light_onSurface else light_onSurface
                        )
                    }
                }
            },
            visualTransformation = when{
                isPasswordField && isPasswordVisible -> VisualTransformation.None
                isPasswordField -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true
        )

    }
}