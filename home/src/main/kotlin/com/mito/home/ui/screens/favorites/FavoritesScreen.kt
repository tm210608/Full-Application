package com.mito.home.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

class FavoritesScreen : ScreenByScaffold {
    override val screenName: ScreenName = ScreenName.FAVORITES

    @Composable
    override fun ContentScaffold(innerPadding: PaddingValues, homeViewModel: HomeViewModel) {
        val viewModel = hiltViewModel<FavoritesViewModel>()
        FavoritesScreen(viewModel, homeViewModel)
    }
}

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, homeViewModel: HomeViewModel) {
    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (status.items.isEmpty()) {
            Text(
                text = "No favorites yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Items you favorite will appear here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
