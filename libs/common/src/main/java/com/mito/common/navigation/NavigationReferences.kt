package com.mito.common.navigation

import com.mito.common.navigation.ScreenName.HOME
import com.mito.common.navigation.ScreenName.LOGIN
import com.mito.common.navigation.ScreenName.PROFILE
import com.mito.common.navigation.ScreenName.WELCOME

//Sealed Class para obtener la referencia de navegacion
sealed class NavigationReferences {
    data object WelcomeReference : NavigationReferences()
    data object LoginReference : NavigationReferences()
    data object HomeReference : NavigationReferences()
    data object ProfileReference : NavigationReferences()

    fun NavigationReferences.getRoute() = when (this) {
        WelcomeReference -> WELCOME.value
        is LoginReference -> "${LOGIN.value}?$OPTIONAL_DATA_KEY={$OPTIONAL_DATA_KEY}"
        is HomeReference -> "${HOME.value}/{$MAIN_DATA_KEY}"
        ProfileReference -> PROFILE.value
    }

    companion object {
        const val MAIN_DATA_KEY = "data"
        const val OPTIONAL_DATA_KEY = "optional"
    }
}

