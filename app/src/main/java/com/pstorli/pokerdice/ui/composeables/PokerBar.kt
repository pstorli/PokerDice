package com.pstorli.pokerdice.ui.composeables

import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme.colorScheme
import com.pstorli.pokerdice.ui.composeables.core.SnackBar

@Composable
fun PokerBar (data: SnackbarData) {
    SnackBar(
        backgroundColor = colorScheme.background,
        borderColor     = colorScheme.outline,
        contentColor    = colorScheme.primary,
        snackbarData    = data
    )
}