@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.login.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mito.common.tools.EMPTY_STRING
import com.mito.common.usecase.Result
import com.mito.login.domain.DummyLoginUseCase
import com.mito.login.domain.Input
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dummyLoginUseCase: DummyLoginUseCase,
) : ViewModel() {

    private val _status: MutableStateFlow<Status> = MutableStateFlow(Status("email1@mail.com","password1"))
    val status: StateFlow<Status> = _status

    private val _event: MutableStateFlow<Event> = MutableStateFlow(Event.None)
    val event: StateFlow<Event> = _event


    fun login() {
        viewModelScope.launch {
            dummyLoginUseCase(Input(status.value.username, status.value.password))
                .onStart {
                    _event.emit(Event.Loading)
                }
                .collect {result ->
                    when (result) {
                        is Result.Error -> {
                            Log.d("Login MITO", "No funcionó el Login")
                            _event.emit(Event.Error(result.message))
                        }

                        is Result.Success<LoginUIModel> -> {
                            Log.d("Login MITO", "Funcionó el Login")
                            _status.value =
                                status.value.copy(userId = (result.value?.userId))
                            _event.emit(Event.Success((result.value?.message ?: "")))
                        }
                        else -> {}
                    }
                }
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _status.value = _status.value.copy(
            username = email,
            password = password,
            loginEnable = isValidEmail(email) && isValidPassword(password)
        )
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun clearEvent() {
        viewModelScope.launch { _event.emit(Event.None) }
    }

    fun isLoading(event: Event) {
        _status.value = status.value.copy(isLoading = event is Event.Loading)
    }
}

sealed class Event {
    data object Loading : Event()
    data class Success(val message: String) : Event()
    data class Error(val message: String) : Event()
    data object None : Event()
}

data class Status(
    val username: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val loginEnable: Boolean = false,
    val isLoading: Boolean = false,
    val userId: Int? = null,
    val sheetValue: SheetValue = SheetValue.Hidden
)