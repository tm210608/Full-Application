package com.mito.menu.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mito.components.resources.container_color
import com.mito.components.resources.content_color

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    exitAction: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent =
        {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(16.dp),
                drawerContainerColor = container_color,
                drawerContentColor = content_color
            ) {
                MenuView(navController = navController, exitAction)
            }
        },
        gesturesEnabled = true,
        content = content
    )
}