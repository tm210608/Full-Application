package com.mito.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mito.common.usecase.Result
import com.mito.login.domain.DummyLoginUseCase
import com.mito.login.domain.Input
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dummyLoginUseCase: DummyLoginUseCase,
) : ViewModel() {

    private val _status: MutableStateFlow<Status> = MutableStateFlow(Status(USERNAME, PASSWORD))
    val status: StateFlow<Status> = _status

    private val _event: MutableStateFlow<Event> = MutableStateFlow(Event.None)
    val event: StateFlow<Event> = _event


    fun login(username: String, password: String) {
        viewModelScope.launch {
            dummyLoginUseCase(Input(username, password))
                .collect {
                    when (it) {
                        is Result.Error -> {
                            Log.d("Login MITO", "No funcionó el Login")
                            _event.emit(Event.Error(it.toString()))
                        }

                        is Result.Success<*> -> {
                            Log.d("Login MITO", "Funcionó el Login")
                            _event.emit(Event.Success(it.value.toString()))
                        }
                    }
                }
        }
    }

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _status.value = Status(email, password)
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(4000)
        _event.emit(Event.Success("Login Successfully"))
        delay(3000)
        _event.emit(Event.None)
        delay(1000)
        _isLoading.value = false
    }

    sealed class Event {
        data object Loading : Event()
        data class Success(val message: String) : Event()
        data class Error(val message: String) : Event()
        data object None : Event()
    }

    data class Status(
        val username: String,
        val password: String,
    )

    companion object {
        private const val USERNAME = ""
        private const val PASSWORD = ""
    }

    fun clearEvent() {
        viewModelScope.launch { _event.emit(Event.None) }
    }
}