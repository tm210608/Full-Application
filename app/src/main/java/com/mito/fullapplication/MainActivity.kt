package com.mito.fullapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mito.fullapplication.ui.theme.FullApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mito.login.ui.LoginScreen
import com.mito.login.ui.LoginViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullApplicationTheme {
                LoginScreen(viewModel = LoginViewModel())
            }
        }
    }
}