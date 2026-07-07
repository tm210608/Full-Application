package com.mito.home.ui.screens.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableStateFlow(Status())
    val status: StateFlow<Status> = _status

    fun onQueryChange(query: String) {
        _status.value = _status.value.copy(query = query)
    }
}

data class Status(
    val query: String = "",
    val results: List<String> = emptyList()
)
