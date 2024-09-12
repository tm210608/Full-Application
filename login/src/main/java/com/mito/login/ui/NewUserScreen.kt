package com.mito.login.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.core.navigation.Screen

class NewUserScreen : Screen {
    override val route: String = NavigationReferences.NewUserReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<NewUserViewModel>()
        NewUserScreen(navController, viewModel)
    }
}

@Composable
fun NewUserScreen(navController: NavHostController, viewModel: NewUserViewModel) {

}