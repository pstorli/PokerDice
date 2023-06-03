package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
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
@Composable
@Suppress("UNUSED_PARAMETER")
fun PokerButton (name: String, color: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {

    OutlinedButton(
        onClick = {
            // Do that click thing.
            onClick.invoke()
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary, containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation  = Consts.DEFAULT_ELEVATION_VAL,
            pressedElevation  = Consts.PRESSED_ELEVATION_VAL,
            disabledElevation = Consts.DISABLED_ELEVATION_VAL
        ),
        shape = RoundedCornerShape(Consts.ROUNDED_CORNER_SHAPE_PCT_VAL),
        modifier = Modifier.padding(16.dp)
    )
    {
        Text(text = name, color = color)
    }
}