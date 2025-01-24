@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.login.ui

import android.util.Patterns
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import com.mito.common.tools.EMPTY_STRING
import com.mito.common.tools.Gender
import com.mito.common.tools.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class NewUserViewModel @Inject constructor(

) : ViewModel() {

    private val _status: MutableStateFlow<NewUserStatus> = MutableStateFlow(NewUserStatus())
    val status: StateFlow<NewUserStatus> = _status

    private val _genderOptions: MutableStateFlow<List<Gender>> =
        MutableStateFlow(listOf(Gender.MALE, Gender.FEMALE))
    val genderOptions: StateFlow<List<Gender>> = _genderOptions.asStateFlow()

    private val _stateOptions: MutableStateFlow<List<State>> =
        MutableStateFlow(enumValues<State>().toList())
    val stateOptions: StateFlow<List<State>> = _stateOptions.asStateFlow()

    data class NewUserStatus(
        val username: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val passwordInfo: PasswordInfo = PasswordInfo(),
        val birthDateInfo: BirthDateInfo = BirthDateInfo(),
        val contactInfo: ContactInfo = ContactInfo(),
        val addressInfo: AddressInfo = AddressInfo(),
        val error: String = EMPTY_STRING,
        val gender: String = EMPTY_STRING,
        val isError: Boolean = false,
        val isButtonEnabled: Boolean = false,
        val sheetValue: SheetValue = SheetValue.Hidden,
    )

    data class PasswordInfo(
        val password: String = EMPTY_STRING,
        val confirmPassword: String = EMPTY_STRING,
    )

    data class BirthDateInfo(
        val birthDate: String = EMPTY_STRING,
        val isBirthdateValid: Boolean = true,
        val isBirthdateError: String? = null,
    )

    data class ContactInfo(
        val numberPhone: String = EMPTY_STRING,
    )

    data class AddressInfo(
        val address: String = EMPTY_STRING,
        val city: String = EMPTY_STRING,
        val state: String = EMPTY_STRING,
    )

    fun onUserNamedChanged(string: String) {
        _status.value = _status.value.copy(username = string)
    }

    fun onEmailChanged(string: String) {
        _status.value = _status.value.copy(email = string)
    }

    fun onPasswordChanged(string: String) {
        _status.value = _status.value.copy(
            passwordInfo = _status.value.passwordInfo.copy(
                password = string
            )
        )
    }

    fun onConfirmPasswordChanged(string: String) {
        _status.value = _status.value.copy(
            passwordInfo = _status.value.passwordInfo.copy(
                confirmPassword = string
            )
        )
    }

    fun onBirthDateChanged(string: String) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val newDate = try {
            LocalDate.parse(string, formatter)
        } catch (e: DateTimeException) {
            _status.update {
                it.copy(
                    birthDateInfo = it.birthDateInfo.copy(
                        birthDate = string,
                        isBirthdateValid = false,
                        isBirthdateError = "Invalid date format. Please use the format dd/MM/yyyy"
                    ),
                    isError = true,
                    error = "Invalid date format. Please use the format dd/MM/yyyy"
                )
            }
            return
        }

        val isInvalidDate = newDate.isAfter(LocalDate.now().minusYears(18).plusDays(1))

        _status.update {
            it.copy(
                birthDateInfo = it.birthDateInfo.copy(
                    birthDate = string,
                    isBirthdateValid = !isInvalidDate,
                    isBirthdateError = if (isInvalidDate) "You must be at least 18 years old" else EMPTY_STRING
                ),
                isError = isInvalidDate,
                error = if (isInvalidDate) "You must be at least 18 years old" else EMPTY_STRING
            )
        }
    }

    fun onNumberPhoneChanged(string: String) {
        _status.value =
            _status.value.copy(contactInfo = _status.value.contactInfo.copy(numberPhone = string))
    }

    fun onAddressChanged(string: String) {
        _status.value =
            _status.value.copy(addressInfo = _status.value.addressInfo.copy(address = string))
    }

    fun onCityChanged(string: String) {
        _status.value =
            _status.value.copy(addressInfo = _status.value.addressInfo.copy(city = string))
    }

    fun onStateChanged(string: String) {
        _status.update { currentStatus ->
            currentStatus.copy(
                addressInfo = _status.value.addressInfo.copy(state = string)
            )
        }
    }

    fun onGenderChanged(string: String) {
        _status.update { currentStatus -> currentStatus.copy(gender = string) }
    }

    fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean {

        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&?¿])[a-zA-Z\\d@#$%&?¿]{8,}\$")
        return passwordRegex.matches(password)
    }

    internal fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    fun isButtonEnabled(status: NewUserStatus): Boolean {

        val updateStatus: NewUserStatus = _status.value.copy(
            isButtonEnabled = isValidEmail(status.email) && isValidPassword(status.passwordInfo.password)
                    && isValidConfirmPassword(
                status.passwordInfo.password,
                status.passwordInfo.confirmPassword
            ) &&
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
