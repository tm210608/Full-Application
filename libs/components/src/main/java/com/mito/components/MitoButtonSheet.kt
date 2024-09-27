@file:OptIn(ExperimentalMaterial3Api::class)

package com.mito.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mito.components.resources.text_h2
import com.mito.components.resources.text_h3

sealed class MitoButtonSheet(
    open val title: Int,
    open val message: Int? = null,
    open val sheetValue: SheetValue,
    open val modifier: Modifier,
    open val button1Text: Int,
    open val button2Text: Int? = null,
    open val onDismissRequest: () -> Unit
) {
    data class CloseAppMitoButtonSheet(
        override val title: Int = R.string.close_app_title_dialog,
        override val message: Int = R.string.close_app_message_dialog,
        val onDismiss: () -> Unit,
        val onConfirm: () -> Unit,
        override val sheetValue: SheetValue,
        override val modifier: Modifier = Modifier,
        override val button1Text: Int = R.string.cancel_button,
        override val button2Text: Int = R.string.confirm_button,
        override val onDismissRequest: () -> Unit
    ) : MitoButtonSheet(title, message, sheetValue, modifier,button1Text, button2Text, onDismissRequest)

    data class InfoMitoButtonSheet(
        override val title: Int,
        override val message: Int? = null,
        val messageString: String? = null,
        override val onDismissRequest: () -> Unit,
        override val modifier: Modifier = Modifier,
        override val sheetValue: SheetValue,
        val buttonText: Int = R.string.accept_button
    ) : MitoButtonSheet(title, message, sheetValue, modifier, button1Text = buttonText, onDismissRequest = onDismissRequest)

    data class ContinueMitoButtonSheet(
        override val title: Int,
        override val message: Int? = null,
        val messageString: String? = null,
        val onAccept: () -> Unit,
        override val sheetValue: SheetValue,
        override val modifier: Modifier = Modifier,
        override val onDismissRequest: () -> Unit,
        val buttonText: Int = R.string.continue_button
    ) : MitoButtonSheet(title, message, sheetValue, modifier, button1Text = buttonText, onDismissRequest = onDismissRequest)
}

@ExperimentalMaterial3Api
@Composable
fun MitoBottomSheet(mitoButtonSheet: MitoButtonSheet) {
    with(mitoButtonSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = SheetState(true, LocalDensity.current, initialValue = sheetValue),
            modifier = modifier
        ) {
            when(this@with) {
                is MitoButtonSheet.CloseAppMitoButtonSheet ->{
                    BottomSheetContentTwoButtons(
                        onDismiss = { onDismiss() },
                        onConfirm = { onConfirm() },
                        onDismissRequest = { onDismissRequest },
                        title = stringResource(id = title),
                        message = stringResource(id = message),
                    )
                }
                is MitoButtonSheet.ContinueMitoButtonSheet -> {
                    BottomSheetContentOneButtons(
                        onAccept = onAccept,
                        onDismissRequest = onDismissRequest,
                        title = stringResource(id = title),
                        message = message?.let { stringResource(id = it) } ?: run { messageString }
                    )
                }
                is MitoButtonSheet.InfoMitoButtonSheet -> {
                    BottomSheetContentOneButtons(
                        onAccept = onDismissRequest,
                        onDismissRequest = onDismissRequest,
                        title = stringResource(id = title),
                        message = message?.let { stringResource(id = it) } ?: run { messageString }
                    )
                }
            }
        }
    }
}


@Composable
fun BottomSheetContentTwoButtons(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    title: String,
    message: String? = null,
    confirmButtonText: Int = R.string.confirm_button,
    dismissButtonText: Int = R.string.cancel_button
) {
    Column {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            style = text_h2
        )
        Text(
            text = message ?: "",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 32.dp),
            style = text_h3
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)) {
            SecondaryButton(
                action = onDismiss,
                text = dismissButtonText,
                modifier = Modifier.weight(1f)
            )
            PrimaryButton(
                action = onConfirm,
                text = confirmButtonText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BottomSheetContentOneButtons(
    onAccept: () -> Unit,
    onDismissRequest: () -> Unit,
    title: String,
    message: String? = null,
    confirmButtonText: Int = R.string.confirm_button,
) {
    Column {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            style = text_h2
        )
        Text(
            text = message ?: "",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 32.dp),
            style = text_h3
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)) {
            PrimaryButton(
                action = onAccept,
                text = confirmButtonText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetTwoButtonsPreview() {
    BottomSheetContentTwoButtons(
        onDismiss = {},
        onConfirm = {},
        onDismissRequest = {},
        title = "Close App",
        message = "Are you sure you want to close the app?"
    )
}
@Preview(showBackground = true)
@Composable
fun BottomSheetOneButtonsPreview() {
    BottomSheetContentOneButtons(
        onAccept = {},
        onDismissRequest = {},
        title = "Close App",
        message = "Error registering user or password"
    )
}