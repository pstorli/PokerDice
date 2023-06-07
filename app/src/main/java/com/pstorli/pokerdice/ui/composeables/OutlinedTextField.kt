package com.pstorli.pokerdice.ui.composeables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.util.Consts

@Composable
fun OutlinedTextField (text: String, color: Color) {
    LabeledRow(
        title = stringResource(id = R.string.cash),
        Consts.color(Consts.COLOR_CASH_NAME, LocalContext.current)
    ) {
        Text(
            text = text,
            color = color
        )
    }
}