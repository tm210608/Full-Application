@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.login.ui

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import com.mito.common.tools.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class NewUserViewModel @Inject constructor(

) : ViewModel() {

    private val _status: MutableStateFlow<NewUserStatus> = MutableStateFlow(NewUserStatus())
    val status: StateFlow<NewUserStatus> = _status

    private val _genderOptions: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf("Male", "Female", "Other"))
    val genderOptions: StateFlow<List<String>> = _genderOptions.asStateFlow()

    private val _stateOptions: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf("Single", "Married", "Divorced"))
    val stateOptions: StateFlow<List<String>> = _stateOptions.asStateFlow()

    data class NewUserStatus(
        val username: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
        val confirmPassword: String = EMPTY_STRING,
        val error: String = EMPTY_STRING,
        val birthDate: String = EMPTY_STRING,
        val numberPhone: String = EMPTY_STRING,
        val address: String = EMPTY_STRING,
        val city: String = EMPTY_STRING,
        val state: String = EMPTY_STRING,
        val gender: String = EMPTY_STRING,
        val isError: Boolean = false,
        val isButtonEnabled: Boolean = false,
        val sheetValue: SheetValue = SheetValue.Hidden,
    )

    fun onUserNamedChanged(string: String) {
        _status.value = _status.value.copy(username = string)
    }

    fun onEmailChanged(string: String) {
        _status.value = _status.value.copy(email = string)
    }

    fun onPasswordChanged(string: String) {
        _status.value = _status.value.copy(password = string)
    }

    fun onConfirmPasswordChanged(string: String) {
        _status.value = _status.value.copy(confirmPassword = string)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onBirthDateChanged(string: String) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(string, formatter)
        val isInvalidDate = date.isAfter(LocalDate.now().minusYears(18).plusDays(1))

        _status.update {
            it.copy(
                birthDate = string,
                isError = isInvalidDate,
                error = if (isInvalidDate) "You must be at least 18 years old" else EMPTY_STRING
            )
        }
    }

    fun onNumberPhoneChanged(string: String) {
        _status.value = _status.value.copy(numberPhone = string)
    }

    fun onAddressChanged(string: String) {
        _status.value = _status.value.copy(address = string)
    }

    fun onCityChanged(string: String) {
        _status.value = _status.value.copy(city = string)
    }

    fun onStateChanged(string: String) {
        _status.update { currentStatus -> currentStatus.copy(state = string) }
    }

    fun onGenderChanged(string: String) {
        _status.update { currentStatus -> currentStatus.copy(gender = string) }
    }

    fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean =
        password.length > 6 && password.contains(char = '@')

    private fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    fun isButtonEnabled(status: NewUserStatus): Boolean {

        val updateStatus: NewUserStatus = _status.value.copy(
            isButtonEnabled = isValidEmail( status.email) && isValidPassword(status.password)
                    && isValidConfirmPassword(status.password, status.confirmPassword) &&
            !status.isError
        )
        _status.update { updateStatus }
        return updateStatus.isButtonEnabled
    }

    fun showConfirmDialog() {
        _status.value = _status.value.copy(sheetValue = SheetValue.Expanded)
    }

    fun hideCloseDialog() {
        _status.value = _status.value.copy(sheetValue = SheetValue.Hidden)
    }
}
