package com.mito.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationController  {

    private lateinit var navController: NavHostController

    @Composable
    fun rememberNavigationController(): NavHostController {
        navController = rememberNavController()
        return navController
    }
}