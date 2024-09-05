package com.mito.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {

    private val _states: MutableStateFlow<States> = MutableStateFlow(States())
    val states: StateFlow<States> = _states

    init {
        viewModelScope.launch {
            _states.collect {
                delay(1500)
                _states.value = _states.value.copy(isAnimated = true)
                delay(2000)
                _states.value = _states.value.copy(cardActivated = true)
                delay(2000)
                _states.value = _states.value.copy(buttonActivated = true)
                delay(1600)
                _states.value = _states.value.copy(buttonEnabled = true)
            }
        }
    }


    data class States(
        val isAnimated: Boolean = false,
        val cardActivated: Boolean = false,
        val buttonActivated: Boolean = false,
        val buttonEnabled: Boolean = false
    )
}
