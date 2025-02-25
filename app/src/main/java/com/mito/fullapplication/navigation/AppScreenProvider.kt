@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.fullapplication.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import com.mito.core.navigation.Screen
import com.mito.core.navigation.ScreenProvider
import com.mito.home.ui.home.HomeScreen
import com.mito.login.ui.LoginScreen
import com.mito.login.ui.NewUserScreen
import com.mito.login.ui.WelcomeScreen

class AppScreenProvider : ScreenProvider {
    override fun getScreens(): List<Screen> {
        return listOf(
            WelcomeScreen(),
            LoginScreen(),
            HomeScreen(),
            NewUserScreen(),
        )
    }
}