package com.mito.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.core.navigation.Screen
import com.mito.login.R

class WelcomeScreen : Screen {
    override val route: String = NavigationReferences.WelcomeReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<WelcomeViewModel>()
        WelcomeScreen(navController, viewModel)
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController, viewModel: WelcomeViewModel) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(Color(0x7CC4F0B8)),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        shape = RoundedCornerShape(25.dp),
    ) {
        InitialScreen(navController= navController, modifier = Modifier)
    }
}

@Composable
fun InitialScreen(navController: NavHostController, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderTextHomeScreen()
        Spacer(modifier = Modifier.padding(20.dp))
        ImageScreen(modifier = Modifier)
        Spacer(modifier = Modifier.padding(15.dp))
        IntroButtonScreen(modifier = Modifier, navController)
    }
}

@Composable
fun HeaderTextHomeScreen() {
    Text(
        text = stringResource(id = R.string.home_screen_full_application),
        fontFamily = FontFamily.Monospace,
        style = MaterialTheme.typography.labelMedium,
        fontSize = 25.sp,
        letterSpacing = 3.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun IntroButtonScreen(modifier: Modifier, navController: NavHostController) {
    Button(
        onClick = { navController.navigate(NavigationReferences.LoginReference.getRoute()) },
        modifier = modifier
            .height(50.dp)
            .padding(8.dp)
            .size(120.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE96D34),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFD6A28A),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    )
    {
        Text(text = stringResource(id = R.string.home_screen_button_text))
    }
}

@Composable
fun ImageScreen(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.full_application_image_header),
        contentDescription = stringResource(id = R.string.home_screen_image_header_content_description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(300.dp)
            .clip(CircleShape),
        alignment = Alignment.Center,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {

    WelcomeScreen(
        navController = NavHostController(LocalContext.current),
        viewModel = WelcomeViewModel()
    )
}
