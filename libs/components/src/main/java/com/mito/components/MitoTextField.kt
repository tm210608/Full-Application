package com.mito.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mito.components.resources.container_color_second
import com.mito.components.resources.primary_button_corner_radius
import com.mito.components.resources.primary_button_padding
import com.mito.components.resources.text_color_error

@Composable
fun MitoTextField(
    value: String,
    textError: Int? = null,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    showTrailingIcon: Boolean = false,
    showPassword: Boolean = false,
    enabled: Boolean,
    titleTextField: Int? = null,
) {
    val showEyeIcon = remember { mutableStateOf(showPassword) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            titleTextField?.let {
                MitoTextBasic(
                    text = stringResource(id = it),
                    modifier = Modifier
                        .padding(end = 2.dp, bottom = 2.dp),
                    color = container_color_second
                )
            }
            textError?.let {
                MitoTextBasic(
                    text = stringResource(id = it),
                    color = text_color_error,
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 2.dp),
                )
            }
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = primary_button_padding),
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (showTrailingIcon) {
                    IconButton(onClick = { showEyeIcon.value = !showEyeIcon.value }) {
                        Icon(
                            painter =
                            if (showEyeIcon.value) painterResource(id = R.drawable.visibility_off_24px)
                            else painterResource(id = R.drawable.visibility_24px),
                            contentDescription = null
                        )
                    }
                }
            },
            visualTransformation = if (showEyeIcon.value) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(primary_button_corner_radius),
            colors = TextFieldDefaults.colors(
                focusedTextColor = if (value.isNotEmpty()) Color(0xFF21241D) else text_color_error,
                unfocusedTextColor = if (value.isNotEmpty()) Color(0xFF394132) else text_color_error,
                focusedContainerColor = Color(0xFF9AE485),
                unfocusedContainerColor = Color(0xFF9AE485),
                focusedIndicatorColor = if (value.isNotEmpty()) Color(0xFFE96D34) else text_color_error,
                unfocusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = if (value.isNotEmpty()) Color(0xFF21241D) else text_color_error,
                unfocusedPlaceholderColor = Color(0xFF394132),
                cursorColor = if (value.isNotEmpty()) Color(0xFFE96D34) else text_color_error,
            ),
            enabled = enabled
        )
    }
}

@Preview
@Composable
fun MitoTextFieldPreview() {
    MitoTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(text = "password") },
        keyboardOptions = KeyboardOptions.Default,
        showTrailingIcon = true,
        showPassword = true,
        titleTextField = R.string.continue_button,
        enabled = true
    )
}

@Preview
@Composable
fun MitoTextFieldError() {
    MitoTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(text = "password") },
        keyboardOptions = KeyboardOptions.Default,
        showPassword = true,
        showTrailingIcon = true,
        titleTextField = R.string.continue_button,
        textError = R.string.cancel_button,
        enabled = true
    )
}