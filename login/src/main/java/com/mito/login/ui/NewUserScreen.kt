package com.mito.login.ui

import android.icu.util.Calendar
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

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<NewUserViewModel>()
        NewUserScreen(navController, viewModel)
    }
}

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
@Composable
fun RegisterFields(
    viewModel: NewUserViewModel,
) {
    val genderOptions by viewModel.genderOptions.collectAsState()
    val stateOptions by viewModel.stateOptions.collectAsState()
    val status by viewModel.status.collectAsState()

    // Calcular la fecha máxima (hoy - 18 años)
    val maxDateCalendar = Calendar.getInstance().apply {
        add(Calendar.YEAR, -18)
    }

    // Calcular la fecha mínima (1900)
    val minDateCalendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1900)
    }

    //Convertir a string las opciones de los dropdowns
    val genderOptionsString = genderOptions.map { it.toString() }
    val stateOptionsString = stateOptions.map { it.toString() }

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
            placeholder = stringResource(com.mito.components.R.string.register_new_user_name),
            onValueChange = { viewModel.onUserNamedChanged(it) },
            isError = false,
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.email,
            title = stringResource(R.string.login_text_field_intro_email),
            textRequired = stringResource(com.mito.components.R.string.register_new_user_required_fields),
            isError = !viewModel.isValidEmail(status.email) && status.email.isNotBlank(),
            textError = stringResource(R.string.register_new_user_email_error),
            placeholder = stringResource(R.string.login_text_field_intro_email),
            onValueChange = { viewModel.onEmailChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldPassword(
            value = status.passwordInfo.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            isError = !viewModel.isValidPassword(status.passwordInfo.password)
                    && status.passwordInfo.password.isNotBlank(),
            textError = stringResource(R.string.register_new_user_password_error),
            title = stringResource(R.string.login_text_field_intro_password),
            textRequired = stringResource(com.mito.components.R.string.register_new_user_required_fields),
            placeholder = stringResource(R.string.login_text_field_intro_password),
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldPassword(
            value = status.passwordInfo.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            isError = !viewModel.isValidConfirmPassword(
                status.passwordInfo.password,
                status.passwordInfo.confirmPassword) && status.passwordInfo.confirmPassword.isNotBlank(),
            textError = stringResource(R.string.register_new_user_confirm_password_error),
            title = stringResource(R.string.login_text_field_intro_confirm_password),
            textRequired = stringResource(com.mito.components.R.string.register_new_user_required_fields),
            placeholder = stringResource(R.string.login_text_field_intro_confirm_password),
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.addressInfo.address,
            title = stringResource(com.mito.components.R.string.register_new_user_address),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_address),
            onValueChange = { viewModel.onAddressChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.contactInfo.numberPhone,
            title = stringResource(com.mito.components.R.string.register_new_user_number_phone),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_number_phone),
            onValueChange = { viewModel.onNumberPhoneChanged(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDataPicker(
            value = status.birthDateInfo.birthDate,
            title = stringResource(com.mito.components.R.string.register_new_user_birth_date),
            textRequired = stringResource(com.mito.components.R.string.register_new_user_required_birthdate),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_birth_date),
            onValueChange = { viewModel.onBirthDateChanged(it) },
            isError = status.isError,
            textError = if (status.isError) status.error else null,
            startDate = minDateCalendar,
            endDate = maxDateCalendar
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldBasic(
            value = status.addressInfo.city,
            title = stringResource(com.mito.components.R.string.register_new_user_city),
            isError = false,
            placeholder = stringResource(com.mito.components.R.string.register_new_user_city),
            onValueChange = { viewModel.onCityChanged(it) }
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDropDown(
            value = status.gender,
            title = stringResource(com.mito.components.R.string.register_new_user_gender),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder_options),
            onValueChange = { viewModel.onGenderChanged(it) },
            options = genderOptionsString
        ).Build()

        Spacer(modifier = Modifier.padding(extra_small_padding))

        MitoTextField.MitoTextFieldDropDown(
            value = status.addressInfo.state,
            title = stringResource(com.mito.components.R.string.register_new_user_state),
            placeholder = stringResource(com.mito.components.R.string.register_new_user_placeholder_options),
            onValueChange = { viewModel.onStateChanged(it) },
            options = stateOptionsString
        ).Build()
    }
}

@ExperimentalMaterial3Api
@Composable
fun ButtonNewUserScreen(
    status: NewUserStatus,
    viewModel: NewUserViewModel,
    onLoginSelected: () -> Unit,
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewUserScreenPreview() {
    NewUserScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = NewUserViewModel()
    )
}
