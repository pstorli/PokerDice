package com.pstorli.pokerdice.ui.composeables.core

import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextField (text: String, value: String, textColor: Color, titleColor: Color=Color.Black, minWidth: Dp =100.dp) {
    LabeledRow(
        title = text,
        titleColor
    ) {
        Text (
            text = value,
            color = textColor,
            modifier = Modifier
                .requiredWidthIn(min = minWidth)
                .widthIn(min = minWidth)
        )
    }
}