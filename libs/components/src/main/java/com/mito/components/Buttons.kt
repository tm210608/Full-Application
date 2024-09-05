package com.mito.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mito.components.resources.content_color_third
import com.mito.components.resources.primary_button_color_container
import com.mito.components.resources.primary_button_color_container_disabled
import com.mito.components.resources.primary_button_color_content
import com.mito.components.resources.primary_button_color_content_disabled
import com.mito.components.resources.primary_button_corner_radius
import com.mito.components.resources.primary_button_font_size
import com.mito.components.resources.primary_button_height
import com.mito.components.resources.primary_button_padding


@Composable
fun PrimaryButton(
    action: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: Int
) {
    Button(
        onClick = { action() },
        modifier = modifier
            .fillMaxWidth()
            .height(primary_button_height)
            .padding(primary_button_padding),
        colors = ButtonDefaults.buttonColors(
            containerColor = primary_button_color_container,
            contentColor = primary_button_color_content,
            disabledContainerColor = primary_button_color_container_disabled,
            disabledContentColor = primary_button_color_content_disabled
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(primary_button_corner_radius)
    ) {
        Text(text = stringResource(id = text), fontSize = primary_button_font_size)
    }
}
@Composable
fun SecondaryButton(
    action: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    text: Int
) {
    Button(
        onClick = { action() },
        modifier = modifier
            .fillMaxWidth()
            .height(primary_button_height)
            .padding(primary_button_padding),
        colors = ButtonDefaults.buttonColors(
            containerColor = content_color_third,
            contentColor = primary_button_color_container,
            disabledContainerColor = primary_button_color_container_disabled,
            disabledContentColor = primary_button_color_content_disabled
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(primary_button_corner_radius)
    ) {
        Text(text = stringResource(id = text), fontSize = primary_button_font_size)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(action = {}, text = R.string.confirm_button)
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    SecondaryButton(action = {}, text = R.string.cancel_button)
}
