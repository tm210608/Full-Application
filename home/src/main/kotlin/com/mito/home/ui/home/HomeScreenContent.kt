package com.mito.home.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.mito.common.navigation.ScreenName
import com.mito.home.ui.screens.favorites.FavoritesScreen
import com.mito.home.ui.screens.main.MainScreen
import com.mito.home.ui.screens.search.SearchScreen
import com.mito.home.ui.screens.settings.SettingsScreen

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenContent(viewModel: HomeViewModel, navController: NavHostController, status: Status) {
    val screens = listOf(
        ScreenName.MAIN,
        ScreenName.SEARCH,
        ScreenName.FAVORITES,
        ScreenName.SETTINGS
    )
    LaunchedEffect(status.screenSelected) {
        status.pagerState.animateScrollToPage(screens.indexOf(status.screenSelected))
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false) {
            viewModel.changeDrawerState(DrawerValue.Closed)
        }) {
        Scaffold(
            topBar = { TopBarHome(navController) { viewModel.changeDrawerState(DrawerValue.Open) } },
            bottomBar = {
                BottomBarHome(status.screenSelected) { selectScreen ->
                    viewModel.selectScreen(selectScreen)
                }
            }
        )
        { innerPadding ->
            HorizontalPager(
                count = screens.size,
                state = status.pagerState,
                modifier = Modifier.padding(innerPadding),
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