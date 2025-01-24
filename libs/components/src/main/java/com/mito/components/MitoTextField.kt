package com.mito.components

import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.DialogProperties
import com.mito.components.resources.extra_small_padding
import com.mito.components.resources.medium_padding
import com.mito.components.resources.small_padding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

sealed class MitoTextField(
    open val value: String,
    open val title: String,
    open val textRequired: String? = null,
    open val isError: Boolean = false,
    open val textError: String?,
    open val placeholder: String?,
    open val leadingIcon: @Composable (() -> Unit)?,
    open val trailingIcon: @Composable (() -> Unit)?,
    open val onValueChange: (String) -> Unit,
    open val readOnly: Boolean = false,
    open val keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    open val enabled: Boolean = true,
    open val visualTransformation: VisualTransformation = VisualTransformation.None,
) {

    data class MitoTextFieldBasic(
        override val value: String,
        override val title: String,
        override val textRequired: String? = null,
        override val isError: Boolean = false,
        override val textError: String? = null,
        override val placeholder: String?,
        override val leadingIcon: @Composable (() -> Unit)? = null,
        override val trailingIcon: @Composable (() -> Unit)? = null,
        override val onValueChange: (String) -> Unit,
        override val readOnly: Boolean = false,
        override val keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        override val enabled: Boolean = true,
        override val visualTransformation: VisualTransformation = VisualTransformation.None,
    ) : MitoTextField(
        value = value,
        title = title,
        textRequired = textRequired,
        isError = isError,
        textError = textError,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        readOnly = readOnly,
        visualTransformation = visualTransformation
    )

    data class MitoTextFieldPassword(
        override val value: String,
        override val onValueChange: (String) -> Unit,
        override val isError: Boolean = false,
        override val textError: String? = null,
        override val title: String,
        override val textRequired: String? = null,
        override val placeholder: String?,
        override val leadingIcon: @Composable (() -> Unit)? = null,
        override val enabled: Boolean = true,
    ) : MitoTextField(
        value = value,
        title = title,
        textRequired  = textRequired,
        isError = isError,
        textError = textError,
        placeholder = placeholder,
        trailingIcon = @Composable { IconButton(onClick = {  }) { } },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        enabled = enabled,
        leadingIcon = leadingIcon,
    )

    data class MitoTextFieldDropDown(
        override val value: String,
        override val title: String,
        override val textRequired: String? = null,
        override val placeholder: String?,
        override val onValueChange: (String) -> Unit,
        override val enabled: Boolean = true,
        val options: List<String>,
    ) : MitoTextField(
        value = value,
        title = title,
        textRequired = textRequired,
        textError = null,
        placeholder = placeholder,
        onValueChange = onValueChange,
        enabled = enabled,
        leadingIcon = null,
        trailingIcon = @Composable { IconButton(onClick = { }) { } },
    )

    data class MitoTextFieldDataPicker(
        override val value: String,
        override val title: String,
        override val textRequired: String? = null,
        override val textError: String? = null,
        override val isError: Boolean = false,
        override val placeholder: String?,
        val startDate: Calendar,
        val endDate: Calendar,
        override val onValueChange: (String) -> Unit,
        override val enabled: Boolean = true,
    ) : MitoTextField(
        value = value,
        title = title,
        textRequired = textRequired,
        placeholder = placeholder,
        isError = isError,
        textError = textError,
        onValueChange = onValueChange,
        enabled = enabled,
        leadingIcon = @Composable { IconButton(onClick = { }) { } },
        trailingIcon = null
    )

    @Composable
    fun Build() = MitoTextField(this)
}

@Composable
private fun MitoTextField(textFieldModel: MitoTextField) {

    when (textFieldModel) {
        is MitoTextField.MitoTextFieldBasic -> MitoTextFieldCustom(textFieldModel)
        is MitoTextField.MitoTextFieldDataPicker -> MitoTextFieldDataPicker(textFieldModel)
        is MitoTextField.MitoTextFieldDropDown -> MitoTextFieldDropDown(textFieldModel)
        is MitoTextField.MitoTextFieldPassword -> MitoTextFieldPassword(textFieldModel)
    }
}

@Composable
private fun MitoTextFieldCustom(textFieldModel: MitoTextField) {

    Column( modifier = Modifier.wrapContentSize()) {
        textFieldModel.textRequired?.let {
            MitoTextBasic(
                text = textFieldModel.textRequired.toString(),
                modifier = Modifier.align(Alignment.Start),
                color = Color.Gray
            )
        }
        OutlinedTextField(
            value = textFieldModel.value,
            onValueChange = textFieldModel.onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = textFieldModel.title) },
            placeholder = { Text(text = textFieldModel.placeholder ?: "") },
            leadingIcon = textFieldModel.leadingIcon,
            trailingIcon = textFieldModel.trailingIcon,
            keyboardOptions = textFieldModel.keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF21241D),
                unfocusedTextColor = Color(0xFF394132),
                focusedContainerColor = Color(0xFF9AE485),
                unfocusedContainerColor = Color(0xFF9AE485),
                focusedIndicatorColor = Color(0xFFE96D34),
                unfocusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color(0xFF21241D),
                unfocusedPlaceholderColor = Color(0xFF394132),
                cursorColor = Color(0xFFE96D34),
            ),
            singleLine = true,
            maxLines = 1,
            visualTransformation = textFieldModel.visualTransformation,
            isError = textFieldModel.isError,
            supportingText = {
                if (textFieldModel.isError) {
                    Text(
                        text = textFieldModel.textError ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            readOnly = textFieldModel.readOnly,
            enabled = true
        )
    }
}

@Composable
private fun MitoTextFieldDropDown(textFieldModel: MitoTextField.MitoTextFieldDropDown) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(" ") }
    var textFieldWidth by remember { mutableStateOf(Size.Zero) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldWidth = coordinates.size.toSize()
            }
    ) {
        MitoTextFieldCustom(
            textFieldModel = MitoTextField.MitoTextFieldBasic(
                value = textFieldModel.value,
                title = textFieldModel.title,
                textError = textFieldModel.textError,
                placeholder = textFieldModel.placeholder,
                trailingIcon = {
                    IconButton(
                        onClick = { expanded = !expanded },
                    ) {
                        Icon(Icons.Filled.KeyboardArrowDown, null)
                    }
                },
                onValueChange = textFieldModel.onValueChange,
                readOnly = true,
                enabled = textFieldModel.enabled,
            )
        )
        if (expanded) {
            DropdownMenu(
                border = BorderStroke(1.dp, Color(0xFFE96D34)),
                tonalElevation = medium_padding,
                shadowElevation = medium_padding,
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldWidth.width.toDp() })
            ) {
                DropdownMenuItem(
                    onClick = { },
                    enabled = false,
                    text = {
                        Text(
                            stringResource(R.string.register_new_user_placeholder_options),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier.padding(extra_small_padding)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE96D34)
                )
                textFieldModel.options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = selectionOption
                            textFieldModel.onValueChange(selectionOption)
                            expanded = false
                        },
                        text = { Text(text = selectionOption) },
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFE96D34)
                    )
                }
            }
        }
    }
}

@Composable
private fun MitoTextFieldPassword(textFieldModel: MitoTextField.MitoTextFieldPassword) {

    var showPassword by remember { mutableStateOf(false) }

    MitoTextFieldCustom(
        textFieldModel = MitoTextField.MitoTextFieldBasic(
            value = textFieldModel.value,
            onValueChange = textFieldModel.onValueChange,
            title = textFieldModel.title,
            textRequired = textFieldModel.textRequired,
            placeholder = textFieldModel.placeholder,
            trailingIcon = {
                IconButton(
                    onClick = {
                        showPassword = !showPassword
                    }
                ) {
                    if (showPassword) {
                        Icon(painter = painterResource(R.drawable.visibility_24px), null)
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.visibility_off_24px),
                            null
                        )
                    }
                }
            },
            leadingIcon = textFieldModel.leadingIcon,
            isError = textFieldModel.isError,
            textError = textFieldModel.textError,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            enabled = textFieldModel.enabled
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MitoTextFieldDataPicker(textFieldModel: MitoTextField.MitoTextFieldDataPicker) {

    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(textFieldModel.value) }

    val startDateMillis = textFieldModel.startDate. timeInMillis
    val endDateMillis = textFieldModel.endDate.timeInMillis

    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(1900, LocalDate.now().year),
        initialSelectedDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in startDateMillis..endDateMillis
            }
        }
    )

    MitoTextFieldCustom(
        textFieldModel = MitoTextField.MitoTextFieldBasic(
            value = selectedDate,
            onValueChange = {
                selectedDate = it
                textFieldModel.onValueChange(it)
            },
            title = textFieldModel.title,
            textRequired = textFieldModel.textRequired,
            placeholder = textFieldModel.placeholder,
            trailingIcon = {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(Icons.Filled.DateRange, null)
                }
            },
            readOnly = true,
            isError = textFieldModel.isError,
            textError = textFieldModel.textError,
            enabled = textFieldModel.enabled
        )
    )
    //Mostrar el DatePicker
    if (expanded) {
        DatePickerDialog(
            onDismissRequest = { expanded = false },
            confirmButton = {
                Button(
                    //Actualizar la fecha seleccionada
                    onClick = {
                        expanded = false

                        val selectedDateMillis = datePickerState.selectedDateMillis

                        if (selectedDateMillis != null) {
                            val formattedDate = SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(selectedDateMillis))

                            selectedDate = formattedDate
                            textFieldModel.onValueChange(formattedDate)
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.confirm_button))
                }
            },
            dismissButton = {
                Button(
                    onClick = { expanded = false }
                ) {
                    Text(text = stringResource(R.string.cancel_button))
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = true
            )
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(small_padding)
                    .align(Alignment.CenterHorizontally),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.birthDate)
                    )
                },
                headline = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        text = stringResource(R.string.select_date)
                    )
                },
                showModeToggle = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MitoTextFieldCustomPreview() {

    var text by remember { mutableStateOf("") }

    MitoTextFieldCustom(
        textFieldModel = MitoTextField.MitoTextFieldBasic(
            value = text,
            title = "Nombre",
            textRequired = "Campo requerido",
            placeholder = "Introduce tu nombre",
            onValueChange = { text = it },
            enabled = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MitoTextFieldDropDownPreview() {

    var text by remember { mutableStateOf("") }
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    val selectedOption by remember { mutableStateOf(options[0]) }
    MitoTextFieldDropDown(
        textFieldModel = MitoTextField.MitoTextFieldDropDown(
            title = "Dropdown",
            placeholder = "Selecciona una opción",
            value = text,
            onValueChange = {
                text = selectedOption
            },
            options = options
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MitoTextFieldPasswordPreview() {
    var text by remember { mutableStateOf("") }

    MitoTextFieldPassword(
        textFieldModel = MitoTextField.MitoTextFieldPassword(
            value = text,
            onValueChange = { text = it },
            title = "Contraseña",
            textRequired = "Campo requerido",
            placeholder = "Introduce tu contraseña",
            enabled = true,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MitoTextFieldDataPickerPreview() {

    MitoTextFieldDataPicker(
        textFieldModel = MitoTextField.MitoTextFieldDataPicker(
            value = "",
            title = "Fecha de nacimiento",
            textRequired = "Campo requerido",
            placeholder = "Selecciona una fecha",
            startDate = Calendar.getInstance(),
            endDate = Calendar.getInstance(),
            onValueChange = {},
            enabled = true
        )
    )
}