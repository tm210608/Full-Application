package com.mito.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.mito.components.resources.container_color_second
import com.mito.components.resources.text_size_12


@Composable
fun MitoTextBasic(
    text: String,
    modifier: Modifier,
    color: Color = container_color_second
){
    Text(
        text = text,
        modifier = modifier,
        fontSize = text_size_12,
        fontWeight = FontWeight.Bold,
        color = color
    )
}