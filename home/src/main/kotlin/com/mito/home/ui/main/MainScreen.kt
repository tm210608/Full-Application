package com.mito.home.ui.main

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

class MainScreen : ScreenByScaffold {
    override val screenName: ScreenName = ScreenName.MAIN

    @Composable
    override fun ContentScaffold(innerPadding: PaddingValues, homeViewModel: HomeViewModel){
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(viewModel, homeViewModel)
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Main Screen")
    }
}