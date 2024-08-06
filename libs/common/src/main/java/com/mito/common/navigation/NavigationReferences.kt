package com.mito.common.navigation

sealed class NavigationReferences(val route: String) {
    data object Login : NavigationReferences("login")
    data object Home : NavigationReferences("home")
    data object Profile : NavigationReferences("profile")
    data object Settings : NavigationReferences("settings")

    fun <T, R>getRoute(data: T? = null, optional: R? = null): String =
        when {
            data != null && optional != null -> "$route/$data?optional=$optional"
            data != null -> "$route/$data"
            else -> route
        }
}