package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.POKER_BTN_MIN_WIDTH

@Composable
fun PokerButton (name: String, textColor: Color, borderColor: Color=MaterialTheme.colorScheme.outline, onClick: () -> Unit) {

    OutlinedButton(
        onClick = {
            // Do that click thing.
            onClick.invoke()
        },
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary, containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation  = Consts.DEFAULT_ELEVATION_VAL,
            pressedElevation  = Consts.PRESSED_ELEVATION_VAL,
            disabledElevation = Consts.DISABLED_ELEVATION_VAL
        ),
        shape           = RoundedCornerShape(Consts.ROUNDED_CORNER_SHAPE_PCT_VAL),
        modifier        = Modifier.padding(4.dp).requiredWidthIn(min = POKER_BTN_MIN_WIDTH).widthIn(min = POKER_BTN_MIN_WIDTH),
        contentPadding  = PaddingValues(2.dp)
    )
    {
        Text(text = name, color = textColor)
    }
}