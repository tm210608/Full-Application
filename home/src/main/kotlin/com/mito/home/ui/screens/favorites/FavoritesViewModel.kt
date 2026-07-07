package com.mito.home.ui.screens.favorites

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableStateFlow(Status())
    val status: StateFlow<Status> = _status
}

data class Status(
    val items: List<String> = emptyList()
)
