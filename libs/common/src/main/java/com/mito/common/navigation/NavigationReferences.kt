package com.mito.common.navigation

import com.mito.common.navigation.ScreenName.LOGIN
import com.mito.common.navigation.ScreenName.HOME
import com.mito.common.navigation.ScreenName.PROFILE
import com.mito.common.navigation.ScreenName.SETTINGS

//Sealed Class para obtener la referencia de navegacion
sealed class NavigationReferences {
    data object LoginReference : NavigationReferences()
    data object HomeReference : NavigationReferences()
    data object ProfileReference : NavigationReferences()
    data object SettingsReference : NavigationReferences()

    fun NavigationReferences.getRoute() = when(this){
        is LoginReference -> "${LOGIN.name}?$OPTIONAL_DATA_KEY={$OPTIONAL_DATA_KEY}"
        is HomeReference -> "${HOME.name}/{$MAIN_DATA_KEY}"
        is ProfileReference -> PROFILE.name
        is SettingsReference -> SETTINGS.name
    }

    companion object{
        const val MAIN_DATA_KEY = "data"
        const val OPTIONAL_DATA_KEY = "optional"
    }
}

