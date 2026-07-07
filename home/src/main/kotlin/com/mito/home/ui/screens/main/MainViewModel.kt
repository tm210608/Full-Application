package com.mito.home.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableStateFlow(Status())
    val status: StateFlow<Status> = _status
}

data class Status(
    val totalTrips: String = "0",
    val savedPlaces: String = "0"
)
