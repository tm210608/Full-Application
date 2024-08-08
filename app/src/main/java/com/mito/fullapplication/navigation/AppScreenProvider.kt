package com.mito.fullapplication.navigation

import com.mito.core.navigation.Screen
import com.mito.core.navigation.ScreenProvider
import com.mito.home.ui.HomeScreen
import com.mito.login.ui.LoginScreen

class AppScreenProvider : ScreenProvider {
    override fun getScreens(): List<Screen> {
        return listOf(
            LoginScreen(),
            HomeScreen()
        )
    }
}