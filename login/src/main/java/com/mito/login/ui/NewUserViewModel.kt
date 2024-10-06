package com.mito.login.ui

import androidx.lifecycle.ViewModel
import com.mito.common.tools.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(

) : ViewModel() {

    private val _status: MutableStateFlow<NewUserStatus> = MutableStateFlow(NewUserStatus())
    val status: StateFlow<NewUserStatus> = _status

    data class NewUserStatus(
        val username: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
        val birthDate: String = EMPTY_STRING,
        val numberPhone: String = EMPTY_STRING,
        val address: String = EMPTY_STRING,
        val city: String = EMPTY_STRING,
        val state: String = EMPTY_STRING,
        val gender: String = EMPTY_STRING,
    )
}
