package com.mito.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mito.common.navigation.ScreenName
import com.mito.components.resources.container_color
import com.mito.components.resources.content_color_disabled_second
import com.mito.components.resources.content_color_second
import com.mito.home.R


@Composable
fun BottomBarHome(screenSelected: ScreenName, action: (ScreenName) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(container_color)
            .padding(bottom = 10.dp)
        ,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconBottomBarHome(
            imageVector = Icons.Default.Home,
            contentDescription = R.string.home_description,
            screenName = ScreenName.MAIN,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Search,
            contentDescription = R.string.search_description,
            screenName = ScreenName.SEARCH,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Favorite,
            contentDescription = R.string.favorites_description,
            screenName = ScreenName.FAVORITES,
            selectScreen = screenSelected,
            action = action
        )
        IconBottomBarHome(
            imageVector = Icons.Default.Settings,
            contentDescription = R.string.settings_description,
            screenName = ScreenName.SETTINGS,
            selectScreen = screenSelected,
            action = action
        )
    }
}

@Composable
fun IconBottomBarHome(
    imageVector: ImageVector,
    contentDescription: Int,
    screenName: ScreenName,
    selectScreen: ScreenName,
    action: (ScreenName) -> Unit
) {
    IconButton(
        onClick = { action(screenName) },
        colors = IconButtonDefaults.iconButtonColors().copy(
            contentColor = content_color_disabled_second,
            disabledContentColor = content_color_second
        ),
        enabled = screenName != selectScreen
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(id = contentDescription),
        )
    }
}