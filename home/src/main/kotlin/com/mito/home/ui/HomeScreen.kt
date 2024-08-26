package com.mito.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.Companion.MAIN_DATA_KEY
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.common.navigation.NavigationRoute
import com.mito.common.navigation.model.LoginNavigationData
import com.mito.core.navigation.Screen

class HomeScreen : Screen {
    override val route: String = NavigationReferences.HomeReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(navController, viewModel)
    }

}

@Composable
fun HomeScreen(navigationController: NavController, viewModel: HomeViewModel) {
    val userId : String = navigationController.currentBackStackEntry?.arguments?.getString(MAIN_DATA_KEY) ?: ""
    val data = LoginNavigationData(optional = "optional_mail@mail.com")
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home Screen $userId", modifier = Modifier
            .align(Alignment.Center)
            .clickable { navigationController.navigate(NavigationRoute.Login(data).navigateTo()) })
    }
}