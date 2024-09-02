package com.mito.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay

class WelcomeScreen : Screen {
    override val route: String = NavigationReferences.WelcomeReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<WelcomeViewModel>()

        WelcomeScreen(navController, viewModel)
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController,viewModel: WelcomeViewModel) {

    var isAnimated by rememberSaveable { mutableStateOf(false) }
    var cardActivated by rememberSaveable { mutableStateOf(false) }
    var buttonActivated by rememberSaveable { mutableStateOf(false) }
    var buttonEnabled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(1500)
        isAnimated = true
        delay(2000)
        cardActivated = true
        delay(1500)
        buttonActivated = true
        delay(500)
        buttonEnabled = true
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        AnimatedVisibility(
            visible = isAnimated,
            enter = slideInVertically(
                animationSpec =
                spring(
                    stiffness = Spring.StiffnessVeryLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                ),
                initialOffsetY = { fullHight -> -fullHight }
            )
        ) {
            Card(
                modifier = Modifier
                    .size(800.dp)
                    .padding(
                        top = 60.dp,
                        start = 25.dp,
                        bottom = 25.dp,
                        end = 25.dp
                    ),
                colors = CardDefaults.cardColors(Color(0x7CC4F0B8)),
                elevation = CardDefaults.elevatedCardElevation(6.dp),
                shape = RoundedCornerShape(25.dp),
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .background(Color.Transparent)
                ) {
                    AnimatedVisibility(
                        visible = cardActivated,
                        enter = slideInHorizontally(
                            animationSpec =
                            spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            ),
                            initialOffsetX = { fullWidth -> -fullWidth }
                        )
                    ) {
                        InitialScreen(
                            modifier = Modifier,
                        )
                    }
                    Spacer(modifier = Modifier.padding(25.dp))
                      AnimatedVisibility(
                          visible = buttonEnabled,
                          enter = slideInHorizontally(
                              animationSpec =
                              spring(
                                  stiffness = Spring.StiffnessVeryLow,
                                  dampingRatio = Spring.DampingRatioMediumBouncy
                              ),
                              initialOffsetX = { fullWidth -> -fullWidth }
                          )
                      ) {
                        Row (
                            Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            IntroButtonScreen(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                enabled = buttonEnabled,
                                navController = navController
                            )
                        }
                      }

                }
            }
        }
    }
}

@Composable
fun InitialScreen(modifier: Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(60.dp))
        HeaderTextHomeScreen(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(8.dp))
        Spacer(modifier = Modifier.padding(30.dp))
        ImageScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun HeaderTextHomeScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.home_screen_full_application),
        modifier = modifier,
        fontFamily = FontFamily.Monospace,
        style = MaterialTheme.typography.labelMedium,
        fontSize = 25.sp,
        letterSpacing = 3.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun IntroButtonScreen(
    modifier: Modifier,
    enabled: Boolean,
    navController: NavHostController,
) {
    Button(
        onClick = { navController.navigate(NavigationReferences.LoginReference.getRoute()) },
        modifier = modifier
            .height(50.dp)
            .padding(8.dp)
            .size(120.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE96D34),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFD6A28A),
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
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
            .size(250.dp)
            .clip(CircleShape),
        alignment = Alignment.Center,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenScreenPreview() {
    val navController = NavHostController(LocalContext.current)
    WelcomeScreen(navController, WelcomeViewModel())
}
