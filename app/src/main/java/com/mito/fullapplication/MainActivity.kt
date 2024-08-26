package com.mito.fullapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mito.core.navigation.ScreenProvider
import com.mito.fullapplication.ui.main.Event
import com.mito.fullapplication.ui.main.MainViewModel
import com.mito.fullapplication.ui.theme.FullApplicationTheme
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
                        screens = screens
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val status = viewModel.status.collectAsState()
    val event = viewModel.event.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello ${status.value.username}!",
            modifier = modifier
        )
        Button(onClick = {
            viewModel.login(status.value.username, status.value.password)
        }) {
            Text(text = "Login")
        }
    }
    when (event.value) {
        is Event.Success -> {
            ResultDialog(
                viewModel = viewModel,
                text = "${(event.value as Event.Success).message} ${status.value.username}"
            )
        }
        is Event.Error -> {
            ResultDialog(
                viewModel = viewModel,
                text = (event.value as Event.Error).message
            )
        }
        else -> {}
    }
}

@Composable
fun ResultDialog(viewModel: MainViewModel, text: String) {
    Dialog(onDismissRequest = {
        viewModel.clearEvent()
    }) {
        Text(
            text = text, modifier = Modifier
                .background(Color.White)
                .padding(40.dp)
        )
    }
}

