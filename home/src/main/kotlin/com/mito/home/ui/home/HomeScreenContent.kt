package com.mito.home.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mito.common.navigation.ScreenName
import com.mito.home.ui.screens.favorites.FavoritesScreen
import com.mito.home.ui.screens.main.MainScreen
import com.mito.home.ui.screens.search.SearchScreen
import com.mito.home.ui.screens.settings.SettingsScreen

@Composable
fun HomeScreenContent(viewModel: HomeViewModel, navController: NavHostController, status: Status) {
    val screens = listOf(
        ScreenName.MAIN,
        ScreenName.SEARCH,
        ScreenName.FAVORITES,
        ScreenName.SETTINGS
    )
    val pagerState = rememberPagerState(pageCount = { screens.size })

    LaunchedEffect(status.screenSelected) {
        val targetPage = screens.indexOf(status.screenSelected)
        if (targetPage != pagerState.currentPage) {
            pagerState.animateScrollToPage(targetPage)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.padding(bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()),
            topBar = { TopBarHome(navController) { viewModel.changeDrawerState(DrawerValue.Open) } },
            bottomBar = {
                BottomBarHome(status.screenSelected) { selectScreen ->
                    viewModel.selectScreen(selectScreen)
                }
            }
        )
        { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                userScrollEnabled = false
            ) {
                when (status.screenSelected) {
                    ScreenName.MAIN -> MainScreen().ContentScaffold(innerPadding, viewModel)
                    ScreenName.SEARCH -> SearchScreen().ContentScaffold(innerPadding, viewModel)
                    ScreenName.FAVORITES -> FavoritesScreen().ContentScaffold(
                        innerPadding,
                        viewModel
                    )
                    ScreenName.SETTINGS -> SettingsScreen().ContentScaffold(innerPadding, viewModel)
                    else -> MainScreen().ContentScaffold(innerPadding, viewModel)
                }
            }
        }
    }
}
