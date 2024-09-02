package com.mito.home.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mito.common.navigation.ScreenName
import com.mito.home.ui.HomeViewModel
import com.mito.home.ui.ScreenByScaffold


class SearchScreen : ScreenByScaffold {
    override val screenName: ScreenName = ScreenName.SEARCH

    @Composable
    override fun ContentScaffold(innerPadding: PaddingValues, homeViewModel: HomeViewModel) {
        val viewModel = hiltViewModel<SearchViewModel>()
        SearchScreen(viewModel, homeViewModel)
    }
}


@Composable
fun SearchScreen(viewModel: SearchViewModel, homeViewModel: HomeViewModel) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Search Screen")
    }
}