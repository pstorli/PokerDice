package com.pstorli.pokerdice.ui.composeables.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.SNACKBAR_FONT_SIZE_SP

@Composable
fun SnackBar (
    snackbarData: SnackbarData,
    backgroundColor: Color = SnackbarDefaults.backgroundColor,
    borderColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
    contentColor: Color = MaterialTheme.colors.surface) {

    Card(
        shape = RoundedCornerShape(Consts.ROUNDED_CORNER_SHAPE_PCT_VAL),
        border = BorderStroke(2.dp, borderColor),
        backgroundColor = backgroundColor,
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text     = snackbarData.message,
                color    = contentColor,
                fontSize = SNACKBAR_FONT_SIZE_SP
            )
        }
    }
}