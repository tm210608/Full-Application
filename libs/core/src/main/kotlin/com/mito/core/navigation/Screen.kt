package com.mito.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Screen {
    val route: String
    @Composable
    fun Content(navController: NavHostController)
}