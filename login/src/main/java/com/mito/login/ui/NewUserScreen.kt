package com.mito.login.ui

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.mito.components.resources.extra_small_padding
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
            ),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderTextNewUser(
                modifier = Modifier,
                text = stringResource(id = R.string.header_name_new_user_screen)
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)))
            NewUserRegister(navController, viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun NewUserRegister(
    navController: NavHostController,
    viewModel: NewUserViewModel,
) {
    val status by viewModel.status.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))

        RegisterFields(viewModel)

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))

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
        } else {
            viewModel.hideCloseDialog()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterFields(
    viewModel: NewUserViewModel,
) {
    val genderOptions by viewModel.genderOptions.collectAsState()
    val stateOptions by viewModel.stateOptions.collectAsState()
    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier
            .padding(extra_small_padding)
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MitoTextField.MitoTextFieldBasic(
            value = status.username,
            title = stringResource(com.mito.components.R.string.register_new_user_name),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onUserNamedChanged(it) },
            isError = false,
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.email,
            title = stringResource(R.string.login_text_field_intro_email),
            isError = !viewModel.isValidEmail(status.email) && status.email.isNotBlank(),
            textError = stringResource(R.string.register_new_user_email_error),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onEmailChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldPassword(
            value = status.password,
            title = stringResource(R.string.login_text_field_intro_password),
            isError = !viewModel.isValidPassword(status.password) && status.password.isNotBlank(),
            textError = stringResource(R.string.register_new_user_password_error),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onPasswordChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldPassword(
            value = status.confirmPassword,
            title = stringResource(R.string.login_text_field_intro_confirm_password),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onConfirmPasswordChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.address,
            title = stringResource(com.mito.components.R.string.register_new_user_address),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onAddressChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.numberPhone,
            title = stringResource(com.mito.components.R.string.register_new_user_number_phone),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onNumberPhoneChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDataPicker(
            value = status.birthDate,
            title = stringResource(com.mito.components.R.string.register_new_user_birth_date),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder_birthdate),
            onValueChange = { viewModel.onBirthDateChanged(it) },
            isError = status.isError,
            textError = if (status.isError) status.error else null,
            startDate = Calendar.getInstance(),
            endDate = Calendar.getInstance()
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.city,
            title = stringResource(com.mito.components.R.string.register_new_user_city),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder),
            onValueChange = { viewModel.onCityChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDropDown(
            value = status.gender,
            title = stringResource(com.mito.components.R.string.register_new_user_gender),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder_options),
            onValueChange = { viewModel.onGenderChanged(it) },
            options = genderOptions
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDropDown(
            value = status.state,
            title = stringResource(com.mito.components.R.string.register_new_user_state),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder_options),
            onValueChange = { viewModel.onStateChanged(it) },
            options = stateOptions
        ).Build()
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewUserScreenPreview() {
    NewUserScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = NewUserViewModel()
    )
}
