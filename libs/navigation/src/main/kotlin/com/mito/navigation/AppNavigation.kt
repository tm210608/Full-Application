package com.mito.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mito.core.navigation.Screen


@Composable
fun AppNavigation(
    navController: NavHostController,
    screens: List<Screen>,
    startScreen: Screen = screens.first(),
) {
    NavHost(navController = navController, startDestination = startScreen.route) {
        screens.forEach { screen ->
            composable(screen.route) { screen.Content(navController) }
        }
    }
}