package com.mito.home.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mito.components.resources.container_color
import com.mito.components.resources.content_color
import com.mito.components.resources.content_color_second
import com.mito.core.navigation.AppInfo
import com.mito.home.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(navigationController: NavController, openDrawer: () -> Unit) {
    TopAppBar(
        title = {
            Text(AppInfo.APP_NAME)
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.settings_description)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = container_color,
            titleContentColor = content_color,
            navigationIconContentColor = content_color_second
        ),
        actions = {
            Image(
                painter = painterResource(id = com.mito.components.R.mipmap.ic_logo),
                contentDescription = AppInfo.APP_NAME,
                Modifier
                    .size(40.dp)
                    .padding(end = 2.dp)
                    .background(content_color, CircleShape)
            )
        }
    )
}