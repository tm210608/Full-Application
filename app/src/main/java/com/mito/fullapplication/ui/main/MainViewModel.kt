package com.mito.fullapplication.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mito.common.tools.EMPTY_STRING
import com.mito.common.usecase.Result
import com.mito.login.domain.DummyLoginUseCase
import com.mito.login.domain.Input
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dummyLoginUseCase: DummyLoginUseCase
) : ViewModel() {

    private val _status : MutableStateFlow<Status> = MutableStateFlow(Status(EMPTY_STRING, EMPTY_STRING))
    val status : StateFlow<Status> = _status
    private val _event : MutableStateFlow<Event> = MutableStateFlow(Event.None)
    val event : StateFlow<Event> = _event


    fun login(username: String, password: String) {
        viewModelScope.launch{
            dummyLoginUseCase(Input(username, password))
                .collect{
                    when(it){
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

    fun clearEvent() {
        viewModelScope.launch { _event.emit(Event.None) }
    }
}

sealed class Event{
    data object Loading : Event()
    data class Success(val message: String) : Event()
    data class Error(val message: String) : Event()
    data object None : Event()
}

data class Status(
    val username: String,
    val password: String
)