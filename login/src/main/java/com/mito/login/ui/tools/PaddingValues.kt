package com.mito.login.ui.tools

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val paddingDefault = Modifier
    .padding(
        start = 25.dp,
        top = 60.dp,
        end = 25.dp,
        bottom = 80.dp
    )
    .fillMaxSize()
    .wrapContentWidth()
    .wrapContentHeight()