@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mito.components.resources.container_color_disabled
import com.mito.components.resources.container_color_second
import com.mito.components.resources.extra_small_padding
import com.mito.components.resources.primary_button_corner_radius
import com.mito.components.resources.primary_button_padding
import com.mito.components.resources.small_padding
import com.mito.components.resources.text_color_error
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
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
    showIsBirthday: Boolean = false,
    showDropdownMenu: Boolean = false,
    enabled: Boolean,
    readOnly: Boolean = false,
    titleTextField: Int? = null,
    options: List<String>? = null,
    onOptionsSelected: ((String) -> Unit)? = null,
) {
    val showEyeIcon = remember { mutableStateOf(showPassword) }
    val expandedDropdown = remember { mutableStateOf(false) }
    var textFieldWidth by remember { mutableStateOf(0.dp) }

    var selectedDate by remember { mutableStateOf(LocalDate.now().minusYears(18)) }
    val minDate = LocalDate.now().minusYears(18)
    val minDateMillis = minDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            .toEpochMilli(),
        minDateMillis
    )

    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            titleTextField?.let {
                MitoTextBasic(
                    text = stringResource(id = it),
                    modifier = Modifier.padding(
                        end = extra_small_padding,
                        bottom = extra_small_padding
                    ),
                    color = container_color_second
                )
            }
            textError?.let {
                MitoTextBasic(
                    text = stringResource(id = it),
                    color = text_color_error,
                    modifier = Modifier.padding(
                        start = extra_small_padding,
                        bottom = extra_small_padding
                    ),
                )
            }
        }
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .background(MaterialTheme.colorScheme.background)
        ) {
            DropdownMenu(
                shadowElevation = small_padding,
                border = BorderStroke(extra_small_padding, container_color_disabled),
                expanded = expandedDropdown.value,
                onDismissRequest = { expandedDropdown.value = false },
                modifier = Modifier
                    .padding(small_padding)
                    .fillMaxWidth(0.92f)
                    .wrapContentHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(container_color_disabled),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.options), color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                options?.forEach { option ->
                    HorizontalDivider()
                    DropdownMenuItem(text = {
                        Text(
                            text = option, color = Color.Black
                        )
                    }, onClick = {
                        onOptionsSelected?.invoke(option)
                        expandedDropdown.value = false
                    }, modifier = Modifier
                        .width(textFieldWidth)
                        .background(Color.White)
                    )
                    HorizontalDivider()
                }
            }
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = primary_button_padding)
                .onGloballyPositioned { coordinates ->
                    textFieldWidth = coordinates.size.width.dp
                },
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (showTrailingIcon && !showDropdownMenu && !showIsBirthday) {
                    IconButton(onClick = { showEyeIcon.value = !showEyeIcon.value }) {
                        Icon(
                            painter = if (showEyeIcon.value) painterResource(id = R.drawable.visibility_off_24px)
                            else painterResource(id = R.drawable.visibility_24px),
                            contentDescription = null
                        )
                    }
                } else if (showTrailingIcon && showDropdownMenu) {
                    IconButton(onClick = { expandedDropdown.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                } else if (showTrailingIcon && showIsBirthday && !showDropdownMenu) {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.birthDate)
                        )
                    }
                }
                if (showDatePicker) {
                    DatePickerDialog(
                        modifier = Modifier.wrapContentSize(),
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(onClick = {
                                showDatePicker = false
                                selectedDate =
                                    Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()


                                onValueChange(selectedDate.format(formatter))
                            }) {
                                Text(text = stringResource(R.string.confirm_button))
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDatePicker = false }) {
                                Text(text = stringResource(R.string.cancel_button))
                            }
                        },
                    ) {
                        DatePicker(
                            state = datePickerState,
                            title = {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(small_padding),
                                        text = stringResource(R.string.select_date)
                                    )
                                }
                            }
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
            enabled = enabled,
            readOnly = readOnly,
        )

    }
}
