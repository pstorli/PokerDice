package com.pstorli.pokerdice.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.util.Consts
@Composable
@Suppress("UNUSED_PARAMETER")
fun PokerButton (name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = {
            // Do that click thing.
            onClick.invoke()
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseOnSurface),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary, containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation  = Consts.DEFAULT_ELEVATION,
            pressedElevation  = Consts.PRESSED_ELEVATION,
            disabledElevation = Consts.DISABLED_ELEVATION
        ),
        shape = RoundedCornerShape(Consts.ROUNDED_CORNER_SHAPE_PCT),
        modifier = Modifier.padding(16.dp)
    )
    {
        Text(text = "Cash $name", color = MaterialTheme.colorScheme.outline)
    }
}