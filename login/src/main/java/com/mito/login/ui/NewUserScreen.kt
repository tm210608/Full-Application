package com.mito.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.components.MitoTextBasic
import com.mito.components.MitoTextField
import com.mito.components.PrimaryButton
import com.mito.components.resources.content_color
import com.mito.components.resources.text_h2
import com.mito.core.navigation.Screen
import com.mito.login.R
import com.mito.login.ui.NewUserViewModel.NewUserStatus

class NewUserScreen : Screen {
    override val route: String = NavigationReferences.NewUserReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<NewUserViewModel>()
        NewUserScreen(navController, viewModel)
    }
}

@Composable
fun NewUserScreen(navController: NavHostController, viewModel: NewUserViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.systemBars
                    .asPaddingValues()
                    .calculateTopPadding(),
                bottom = WindowInsets.systemBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ),
        color = colorResource(id = R.color.surface_login_and_register_color)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .wrapContentSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .background(
                    Color.White
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NewUserRegister(viewModel, navController)
        }
    }
}

@Composable
fun NewUserRegister(
    viewModel: NewUserViewModel,
    navController: NavHostController,
) {

    val status by viewModel.status.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center,
        modifier = Modifier
            .height(70.dp)
            .padding(dimensionResource(id = R.dimen.padding_small))
            .background(color = colorResource(id = R.color.containerButtonColor))
            .fillMaxSize()
    )
    {
        HeaderTextNewUser(text = stringResource(id = R.string.header_name_new_user_screen))
    }
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MitoTextField(
            value = status.username,
            onValueChange = { viewModel.onUserNamedChanged(it) },
            placeholder = { Text(text = stringResource(id = R.string.register_new_user_name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            enabled = true,
            titleTextField = R.string.register_new_user_name
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

        MitoTextField(
            value = status.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            enabled = true,
            titleTextField = R.string.login_text_field_intro_email
        )


        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.login_text_field_intro_password),
            modifier = Modifier.align(Alignment.Start)
        )
        PasswordNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_birth_date),
            modifier = Modifier.align(Alignment.Start)
        )
        BirthDateNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_number_phone),
            modifier = Modifier.align(Alignment.Start)
        )
        NumberPhoneNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_address),
            modifier = Modifier.align(Alignment.Start)
        )
        AddressNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_city),
            modifier = Modifier.align(Alignment.Start)
        )
        CityNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_state),
            modifier = Modifier.align(Alignment.Start)
        )
        StateNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
        MitoTextBasic(
            text = stringResource(id = R.string.register_new_user_gender),
            modifier = Modifier.align(Alignment.Start)
        )
        GenderNewUserScreen(status) { }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Default)))
        ButtonNewUserScreen(status) { }
    }
}

@Composable
fun ButtonNewUserScreen(
    status: NewUserStatus,
    onLoginSelected: () -> Unit,
) {
    PrimaryButton(
        action = { onLoginSelected() },
        text = R.string.register_new_user
    )
}

@Composable
fun GenderNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.gender,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_gender)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        enabled = true
    )
}

@Composable
fun StateNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.state,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_state)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        enabled = true
    )
}

@Composable
fun CityNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.city,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_city)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        enabled = true
    )
}

@Composable
fun AddressNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.address,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_address)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        enabled = true
    )
}

@Composable
fun NumberPhoneNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.numberPhone,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_number_phone)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        enabled = true
    )
}

@Composable
fun BirthDateNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.birthDate,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_birth_date)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        enabled = true
    )
}

@Composable
fun PasswordNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.password,
        onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_password)) },
        showTrailingIcon = true,
        showPassword = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        enabled = true
    )
}

@Composable
fun EmailNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.email,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        enabled = true
    )
}

@Composable
fun NameNewUserScreen(
    status: NewUserStatus,
    onTextFieldChanged: (String) -> Unit,
) {
    MitoTextField(
        value = status.username,
        onValueChange = onTextFieldChanged,
        placeholder = { Text(text = stringResource(id = R.string.register_new_user_name)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        enabled = true
    )
}

@Composable
fun HeaderTextNewUser(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        style = text_h2,
        color = content_color,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewUserScreenPreview() {
    NewUserScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = NewUserViewModel()
    )
}