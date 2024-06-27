package com.sd.lib.compose.inputgroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FInputGroup(
    state: InputGroupState,
    modifier: Modifier = Modifier,
    itemSpace: Dp = 20.dp,
    placeholder: String = "●",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusRequester: (FocusRequester) -> Unit = { it.requestFocus() },
    item: @Composable (String) -> Unit = { FInputGroupItem(value = it) },
) {
    val onFocusRequesterUpdated by rememberUpdatedState(newValue = onFocusRequester)
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        onFocusRequesterUpdated(focusRequester)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        InputView(
            value = state.value,
            onValueChange = { state.notifyValue(it) },
            focusRequester = focusRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.matchParentSize(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(itemSpace, Alignment.CenterHorizontally),
        ) {
            repeat(state.count) { index ->
                val value = state.getIndexValue(index)
                    .takeIf { it.isEmpty() || placeholder.isEmpty() }
                    ?: placeholder
                item(value)
            }
        }
    }
}

@Composable
private fun InputView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
) {
    val emptyTextToolbar = remember {
        object : TextToolbar {
            override val status: TextToolbarStatus = TextToolbarStatus.Hidden
            override fun hide() = Unit
            override fun showMenu(
                rect: Rect,
                onCopyRequested: (() -> Unit)?,
                onPasteRequested: (() -> Unit)?,
                onCutRequested: (() -> Unit)?,
                onSelectAllRequested: (() -> Unit)?,
            ) = Unit
        }
    }

    val emptyTextSelectionColors = remember {
        TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        )
    }

    CompositionLocalProvider(
        LocalTextToolbar provides emptyTextToolbar,
        LocalTextSelectionColors provides emptyTextSelectionColors,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            cursorBrush = SolidColor(Color.Transparent),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = {},
            modifier = modifier.focusRequester(focusRequester)
        )
    }
}

@Composable
fun FInputGroupItem(
    modifier: Modifier = Modifier,
    value: String,
    style: TextStyle = TextStyle.Default,
    width: Dp = 50.dp,
    height: Dp = width,
    color: Color = Color(0xFFEEEEEE),
    shape: Shape = RoundedCornerShape(6.dp),
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(color, shape),
        contentAlignment = Alignment.Center,
    ) {
        BasicText(
            text = value,
            style = style,
        )
    }
}