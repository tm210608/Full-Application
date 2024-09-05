package com.mito.login.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.components.PrimaryButton
import com.mito.components.resources.content_color_disabled
import com.mito.core.navigation.AppInfo
import com.mito.core.navigation.Screen
import com.mito.login.R
import com.mito.login.ui.tools.headerTextHomeScreen
import com.mito.login.ui.tools.paddingDefault

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

    val states by viewModel.states.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        AnimatedVisibility(
            visible = states.isAnimated,
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
                modifier = paddingDefault,
                colors = CardDefaults.cardColors(colorResource(id = R.color.card_Colors)),
                elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.card_Elevation)),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_Shape)),
            ) {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .background(Color.Transparent)
                ) {
                    AnimatedVisibility(
                        visible = states.cardActivated,
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
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_Medium_Padding)))
                      AnimatedVisibility(
                          visible = states.buttonActivated,
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
                                enabled = states.buttonEnabled,
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
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_Large_Padding)))
        HeaderTextHomeScreen(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(8.dp))
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_Medium_Padding)))
        ImageScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = AppInfo.APP_VERSION,
            color = content_color_disabled,modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun HeaderTextHomeScreen(modifier: Modifier = Modifier) {

    Text(
        text = stringResource(id = R.string.home_screen_full_application),
        modifier = modifier,
        style = headerTextHomeScreen.labelMedium
    )
}

@Composable
fun IntroButtonScreen(modifier: Modifier, navController: NavHostController, enabled: Boolean) {
    PrimaryButton(
        action = { navController.navigate(NavigationReferences.LoginReference.getRoute()) },
        text = R.string.home_screen_button_text,
        modifier = modifier,
        isEnabled = enabled
    )
}

@Composable
fun ImageScreen(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.full_application_image_header),
        contentDescription = stringResource(id = R.string.home_screen_image_header_content_description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_Size))
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
