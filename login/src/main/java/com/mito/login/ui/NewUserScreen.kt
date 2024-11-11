package com.mito.login.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.components.MitoBottomSheet
import com.mito.components.MitoButtonSheet
import com.mito.components.MitoTextField
import com.mito.components.PrimaryButton
import com.mito.components.resources.content_color
import com.mito.components.resources.text_h2
import com.mito.core.navigation.Screen
import com.mito.login.R
import com.mito.login.ui.NewUserViewModel.NewUserStatus

@ExperimentalMaterial3Api
class NewUserScreen : Screen {
    override val route: String = NavigationReferences.NewUserReference.getRoute()

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<NewUserViewModel>()
        NewUserScreen(navController, viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
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
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderTextNewUser(
                modifier = Modifier,
                text = stringResource(id = R.string.header_name_new_user_screen)
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
            NewUserRegister(viewModel, navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun NewUserRegister(
    viewModel: NewUserViewModel,
    navController: NavHostController,
) {

    val status by viewModel.status.collectAsState()
    val genderOptions by viewModel.genderOptions.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
    ) {
        item {

            MitoTextField(
                value = status.username,
                onValueChange = { viewModel.onUserNamedChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_name)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.register_new_user_name,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.email,
                textError = R.string.new_user_text_field_email_error,
                onValueChange = { viewModel.onEmailChanged(it)},
                placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.login_text_field_intro_email,
            )


            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.password,
                textError = R.string.new_user_text_field_password_error,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                showTrailingIcon = true,
                showPassword = true,
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.login_text_field_intro_password,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.login_text_field_intro_confirm_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                showTrailingIcon = true,
                showPassword = true,
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.login_text_field_intro_confirm_password,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.birthDate,
                onValueChange = { viewModel.onBirthDateChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_birth_date)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
                showTrailingIcon = true,
                showIsBirthday = true,
                showDropdownMenu = false,
                enabled = true,
                readOnly = true,
                titleTextField = R.string.register_new_user_birth_date,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.numberPhone,
                onValueChange = { viewModel.onNumberPhoneChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_number_phone)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.register_new_user_number_phone,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.address,
                onValueChange = { viewModel.onAddressChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_address)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.register_new_user_address,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.city,
                onValueChange = { viewModel.onCityChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_city)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.register_new_user_city,
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.state,
                onValueChange = { viewModel.onStateChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_state)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                showDropdownMenu = false,
                enabled = true,
                titleTextField = R.string.register_new_user_state,
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))

            MitoTextField(
                value = status.gender,
                onValueChange = { viewModel.onGenderChanged(it) },
                placeholder = { Text(text = stringResource(id = R.string.register_new_user_gender)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                showTrailingIcon = true,
                showDropdownMenu = true,
                enabled = true,
                readOnly = true,
                titleTextField = R.string.register_new_user_gender,
                options = genderOptions,
                onOptionsSelected = { selectedGender -> viewModel.onGenderChanged(selectedGender) },
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_Default)))

            ButtonNewUserScreen(status, viewModel) {
                viewModel.showConfirmDialog()
            }
            if (status.sheetValue == SheetValue.Expanded) {
                MitoBottomSheet(
                    mitoButtonSheet = MitoButtonSheet.CloseAppMitoButtonSheet(
                        title = R.string.confirm_action,
                        message = R.string.confirm_register_new_user,
                        onDismissRequest = { viewModel.hideCloseDialog() },
                        onDismiss = { viewModel.hideCloseDialog() },
                        onConfirm = { navController.navigate(NavigationReferences.LoginReference.getRoute()) },
                        sheetValue = status.sheetValue
                    )
                )
            }else{
                viewModel.hideCloseDialog()
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ButtonNewUserScreen(
    status: NewUserStatus,
    viewModel: NewUserViewModel,
    onLoginSelected: () -> Unit
) {
    PrimaryButton(
        action = onLoginSelected,
        text = R.string.register_new_user,
        isEnabled = viewModel.isButtonEnabled(status)
    )
}

@Composable
fun HeaderTextNewUser(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.containerButtonColor))
            .height(dimensionResource(R.dimen.button_Height)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = text_h2,
            color = content_color,
            modifier = modifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewUserScreenPreview() {
    NewUserScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = NewUserViewModel()
    )
}
