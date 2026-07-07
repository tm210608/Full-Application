package com.mito.home.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mito.common.navigation.ScreenName
import com.mito.home.ui.ScreenByScaffold
import com.mito.home.ui.home.HomeViewModel

class SettingsScreen : ScreenByScaffold {
    override val screenName: ScreenName = ScreenName.SETTINGS

    @Composable
    override fun ContentScaffold(innerPadding: PaddingValues, homeViewModel: HomeViewModel) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        SettingsScreen(viewModel, homeViewModel)
    }
}

@Composable
fun SettingsScreen(viewModel: SettingsViewModel, homeViewModel: HomeViewModel) {
    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
        status.settingsItems.forEach { item ->
            SettingsItem(
                title = item.title,
                subtitle = item.subtitle
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun SettingsItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
