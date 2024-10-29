@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.login.ui

import android.util.Patterns
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import com.mito.common.tools.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class NewUserViewModel @Inject constructor(

) : ViewModel() {

    private val _status: MutableStateFlow<NewUserStatus> = MutableStateFlow(NewUserStatus())
    val status: StateFlow<NewUserStatus> = _status

    data class NewUserStatus(
        val username: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
        val confirmPassword: String = EMPTY_STRING,
        val birthDate: String = EMPTY_STRING,
        val numberPhone: String = EMPTY_STRING,
        val address: String = EMPTY_STRING,
        val city: String = EMPTY_STRING,
        val state: String = EMPTY_STRING,
        val gender: String = EMPTY_STRING,
        val isButtonEnabled: Boolean = false,
        val sheetValue: SheetValue = SheetValue.Hidden
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

    fun onBirthDateChanged(string: String) {
        _status.value = _status.value.copy(birthDate = string)
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
        _status.value = _status.value.copy(state = string)
    }

    fun onGenderChanged(string: String) {
        _status.value = _status.value.copy(gender = string)
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean =
        password.length > 6 && password.contains(char = '@')

    private fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    fun isButtonEnabled(status: NewUserStatus): Boolean {
        val updateStatus: NewUserStatus = _status.value.copy(
              isButtonEnabled = isValidEmail(status.email) && isValidPassword(status.password) && isValidConfirmPassword(status.password, status.confirmPassword)
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
