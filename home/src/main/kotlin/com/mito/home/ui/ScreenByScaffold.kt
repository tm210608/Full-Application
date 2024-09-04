package com.mito.home.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.mito.common.navigation.ScreenName
import com.mito.home.ui.home.HomeViewModel

interface ScreenByScaffold {
    val screenName: ScreenName
    @Composable
    fun ContentScaffold(innerPadding: PaddingValues, homeViewModel: HomeViewModel)
}