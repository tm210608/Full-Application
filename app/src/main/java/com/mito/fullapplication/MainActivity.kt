package com.mito.fullapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mito.core.navigation.ScreenProvider
import com.mito.fullapplication.ui.theme.FullApplicationTheme
import com.mito.login.ui.WelcomeScreen
import com.mito.navigation.AppNavigation
import com.mito.navigation.NavigationController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var screenProvider: ScreenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navigationController = remember { NavigationController() }
                    val navController = navigationController.rememberNavigationController()
                    val screens = screenProvider.getScreens()
                    AppNavigation(
                        navController = navController,
                        screens = screens,
                        startScreen = screens.first { it is WelcomeScreen }
                    )
                }
            }
        }
    }
}

