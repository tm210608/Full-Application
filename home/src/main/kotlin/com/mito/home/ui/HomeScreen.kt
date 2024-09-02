package com.mito.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.Companion.MAIN_DATA_KEY
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.common.navigation.ScreenName
import com.mito.components.resources.container_color
import com.mito.components.resources.content_color
import com.mito.components.resources.content_color_disabled_second
import com.mito.components.resources.content_color_second
import com.mito.core.navigation.AppInfo
import com.mito.core.navigation.Screen
import com.mito.home.R
import com.mito.home.ui.favorites.FavoritesScreen
import com.mito.home.ui.main.MainScreen
import com.mito.home.ui.search.SearchScreen
import com.mito.home.ui.settings.SettingsScreen
import com.mito.components.R as libsR

class HomeScreen : Screen {
    override val route: String = NavigationReferences.HomeReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(navController, viewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel) {
    navController.currentBackStackEntry?.arguments?.getString(MAIN_DATA_KEY)?.let {userId->
        viewModel.setUserId(userId)
    }
    val status by viewModel.status.collectAsState()
    val screens = listOf(
        ScreenName.MAIN,
        ScreenName.SEARCH,
        ScreenName.FAVORITES,
        ScreenName.SETTINGS
    )
    val pagerState = rememberPagerState()

    LaunchedEffect(status.screenSelected) {
        pagerState.animateScrollToPage(screens.indexOf(status.screenSelected))
    }

    Scaffold(
        topBar = { TopBarHome(navController) },
        bottomBar = {
            BottomBarHome(status.screenSelected) { selectScreen ->
                viewModel.selectScreen(selectScreen)
            }
        }
    )
    { innerPadding ->
        HorizontalPager(
            count = screens.size,
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) {
            when (status.screenSelected) {
                ScreenName.MAIN -> MainScreen().ContentScaffold(innerPadding, viewModel)
                ScreenName.SEARCH -> SearchScreen().ContentScaffold(innerPadding, viewModel)
                ScreenName.FAVORITES -> FavoritesScreen().ContentScaffold(innerPadding, viewModel)
                ScreenName.SETTINGS -> SettingsScreen().ContentScaffold(innerPadding, viewModel)
                else -> MainScreen().ContentScaffold(innerPadding, viewModel)
            }
        }
    }
}

@Composable
fun BottomBarHome(screenSelected: ScreenName, action: (ScreenName) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(container_color)
        .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconBottomBarHome(
            imageVector = Icons.Default.Home,
            contentDescription = R.string.home_description,
            screenName = ScreenName.MAIN,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Search,
            contentDescription = R.string.search_description,
            screenName = ScreenName.SEARCH,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Favorite,
            contentDescription = R.string.favorites_description,
            screenName = ScreenName.FAVORITES,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Settings,
            contentDescription = R.string.settings_description,
            screenName = ScreenName.SETTINGS,
            selectScreen = screenSelected,
            action = action
        )
    }
}

@Composable
fun IconBottomBarHome(
    imageVector: ImageVector,
    contentDescription: Int,
    screenName: ScreenName,
    selectScreen: ScreenName,
    action: (ScreenName) -> Unit
){
    IconButton(
        onClick = { action(screenName) },
        colors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = content_color_disabled_second,
            disabledContentColor = content_color_second
        ),
        enabled = screenName != selectScreen
    ) {
        Icon(
            imageVector =  imageVector,
            contentDescription = stringResource(id = contentDescription),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(navigationController: NavController) {
    TopAppBar(
        title = {
                Text(AppInfo.APP_NAME)
        },
        navigationIcon = {
            IconButton(onClick = { /* Acción de apertura de menú */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.settings_description)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = container_color,
            titleContentColor = content_color,
            navigationIconContentColor = content_color_second
        ),
        actions = {
            Image(
                painter = painterResource(id = libsR.mipmap.ic_logo),
                contentDescription = AppInfo.APP_NAME,
                Modifier
                    .size(40.dp)
                    .padding(end = 2.dp)
                    .background(content_color, CircleShape)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(NavHostController(LocalContext.current), viewModel = HomeViewModel())
}

