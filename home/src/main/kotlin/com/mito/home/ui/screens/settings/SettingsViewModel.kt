package com.mito.home.ui.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _status = MutableStateFlow(Status())
    val status: StateFlow<Status> = _status
}

data class Status(
    val settingsItems: List<SettingsItem> = listOf(
        SettingsItem("Account", "Manage your account information"),
        SettingsItem("Notifications", "Configure push notifications"),
        SettingsItem("Privacy", "Control your privacy settings"),
        SettingsItem("About", "App version and information")
    )
)

data class SettingsItem(
    val title: String,
    val subtitle: String
)
