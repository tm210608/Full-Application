@file:OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)

package com.mito.home.ui.home

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.lifecycle.ViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.mito.common.navigation.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@OptIn(ExperimentalPagerApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _status: MutableStateFlow<Status> = MutableStateFlow(Status())
    val status: StateFlow<Status> = _status

    fun setUserId(userId: String) {
        _status.value = status.value.copy(userId = userId.toInt())
    }

    fun selectScreen(screenSelected: ScreenName) {
        _status.value = status.value.copy(screenSelected = screenSelected)
    }

    fun changeDrawerState(drawerValue: DrawerValue) {
        _status.value = status.value.copy(drawerState = DrawerState(drawerValue))
    }

    fun showCloseDialog() {
        _status.value = _status.value.copy(sheetValue = SheetValue.Expanded)
    }

    fun hideCloseDialog() {
        _status.value = _status.value.copy(sheetValue = SheetValue.Hidden)
    }
}

data class Status(
    val userId : Int = 0,
    val screenSelected : ScreenName = ScreenName.MAIN,
    val drawerState : DrawerState = DrawerState(DrawerValue.Closed),
    val pagerState : PagerState = PagerState(),
    val sheetValue: SheetValue = SheetValue.Hidden
)