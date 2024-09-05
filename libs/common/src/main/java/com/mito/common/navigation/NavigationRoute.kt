package com.mito.common.navigation

import com.mito.common.navigation.model.HomeNavigationData

//Sealed Class para obtener la ruta de navegación desde cada pantalla
sealed class NavigationRoute {
    data object Welcome : NavigationRoute()
    data object Login : NavigationRoute()
    data class Home(val data: HomeNavigationData) : NavigationRoute()
    data object Profile : NavigationRoute()

    fun navigateTo() =
        when (this) {
            Welcome -> getNavigationRoute(ScreenName.WELCOME)
            is Home -> getNavigationRoute(ScreenName.HOME, data)
            Login -> getNavigationRoute(ScreenName.LOGIN)
            Profile -> getNavigationRoute(ScreenName.PROFILE)
        }

    private fun getNavigationRoute(
        screenName: ScreenName,
        data: NavigationData<*, *>? = null,
    ) = with(data) {
        when {
            this == null -> screenName.name
            optional != null && value != null -> "${screenName.name}/$value?optional=${optional}"
            optional != null && value == null -> "${screenName.name}?optional=${optional}"
            optional == null && value != null -> "${screenName.name}/$value"
            else -> ""
        }
    }
}