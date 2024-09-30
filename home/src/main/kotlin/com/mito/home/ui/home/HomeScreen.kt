@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.home.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mito.common.navigation.NavigationReferences
import com.mito.common.navigation.NavigationReferences.Companion.MAIN_DATA_KEY
import com.mito.common.navigation.NavigationReferences.ProfileReference.getRoute
import com.mito.components.MitoButtonSheet
import com.mito.components.MitoBottomSheet
import com.mito.components.resources.padding_top_bottom_sheet_dialog_on_scaffold_to_fix
import com.mito.core.navigation.Screen
import com.mito.menu.ui.NavigationDrawer

class HomeScreen : Screen {
    override val route: String = NavigationReferences.HomeReference.getRoute()

    @Composable
    override fun Content(navController: NavHostController) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(navController, viewModel)
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel) {
    navController.currentBackStackEntry?.arguments?.getString(MAIN_DATA_KEY)?.let { userId ->
        viewModel.setUserId(userId)
    }
    val status by viewModel.status.collectAsState()

    BackHandler {
        viewModel.showCloseDialog()
    }
    if (status.sheetValue != SheetValue.Hidden) {
        MitoBottomSheet(mitoButtonSheet = getButtonSheet(viewModel,status,navController))
    }
    NavigationDrawer(
        drawerState = status.drawerState,
        navController = navController,
        exitAction = { viewModel.showCloseDialog() }
    ) {
        HomeScreenContent(viewModel, navController, status)
    }
}

fun getButtonSheet(
    viewModel: HomeViewModel,
    status: Status,
    navController: NavHostController
): MitoButtonSheet.CloseAppMitoButtonSheet =
    MitoButtonSheet.CloseAppMitoButtonSheet(
        onDismissRequest = { viewModel.hideCloseDialog() },
        onDismiss = { viewModel.hideCloseDialog() },
        onConfirm = {
            viewModel.hideCloseDialog()
            navController.navigateUp()
        },
        sheetValue = status.sheetValue,
        modifier = Modifier.padding(top = padding_top_bottom_sheet_dialog_on_scaffold_to_fix),
    )

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(NavHostController(LocalContext.current), viewModel = HomeViewModel())
}

