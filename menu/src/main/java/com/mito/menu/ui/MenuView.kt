package com.mito.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mito.components.resources.container_color_second
import com.mito.components.resources.content_color
import com.mito.menu.R

@Composable
fun MenuView(navController: NavHostController, exitAction: () -> Unit) {
    Box (modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
    ){
        Column(modifier = Modifier
            .fillMaxHeight()) {
            MenuItemView(
                title = stringResource(id = R.string.profile_menu_text),
                icon = Icons.Default.Person,
                contentDescription = stringResource(id = R.string.profile_menu_content_description)
            ) {
                //navigate to Profile screen
            }
            MenuItemView(
                title = stringResource(id = R.string.about_us_menu_text),
                icon = Icons.Default.Info,
                contentDescription = stringResource(id = R.string.about_us_menu_content_description)
            ) {
                //navigate to About Us screen
            }
            MenuItemView(
                title = stringResource(id = R.string.notifications_menu_text),
                icon = Icons.Default.Notifications,
                contentDescription = stringResource(id = R.string.notifications_menu_content_description)
            ) {
                //navigate to Notifications screen
            }
            MenuItemView(
                title = stringResource(id = R.string.feedback_menu_text),
                icon = Icons.Default.Email,
                contentDescription = stringResource(id = R.string.feedback_menu_content_description)
            ) {
                //navigate to Feedback screen
            }

        }
        Column (modifier = Modifier.align(Alignment.BottomCenter)){
            HorizontalDivider(color = container_color_second, thickness = 2.dp)
            MenuItemView(
                title = stringResource(id = R.string.exit_menu_text),
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = stringResource(id = R.string.exit_menu_content_description),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                exitAction()
            }
        }
    }
}

@Composable
fun MenuItemView(
    title: String,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors().copy(
            containerColor = container_color_second,
            contentColor = content_color
        )
    ) {
        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                fontSize = 16.sp
            )
            Icon(imageVector = icon, contentDescription = contentDescription)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE96D34)
@Composable
fun MenuViewPreview() {
    MenuView(NavHostController(LocalContext.current), {})
}