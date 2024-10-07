package com.mito.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mito.components.resources.primary_button_corner_radius
import com.mito.components.resources.primary_button_padding

@Composable
fun MitoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    showTrailingIcon: Boolean = false,
    showPassword: Boolean = false,
    enabled: Boolean,
    titleTextField: Int? = null
) {
    val showEyeIcon = remember { mutableStateOf(showPassword) }

    Column {
        titleTextField?.let {
            MitoTextBasic(
                text = stringResource(id = it),
                modifier = Modifier.align(Alignment.Start).padding(bottom = 2.dp)
            )
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
                            if (showEyeIcon.value) painterResource(id = R.drawable.visibility_24px)
                            else painterResource(id = R.drawable.visibility_off_24px),
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
                focusedTextColor = Color(0xFF21241D),
                unfocusedTextColor = Color(0xFF394132),
                focusedContainerColor = Color(0xFF9AE485),
                unfocusedContainerColor = Color(0xFF9AE485),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            enabled = enabled
        )
    }

}