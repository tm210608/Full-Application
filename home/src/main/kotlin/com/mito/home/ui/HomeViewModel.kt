package com.mito.home.ui

import androidx.lifecycle.ViewModel
import com.mito.common.navigation.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _status: MutableStateFlow<Status> = MutableStateFlow(Status(screenSelected = ScreenName.MAIN))
    val status: StateFlow<Status> = _status

    fun setUserId(userId: String) {
        _status.value = status.value.copy(userId = userId.toInt())
    }

    fun selectScreen(screenSelected: ScreenName) {
        _status.value = status.value.copy(screenSelected = screenSelected)
    }
}

data class Status(
    val userId : Int = 0,
    val screenSelected : ScreenName
)