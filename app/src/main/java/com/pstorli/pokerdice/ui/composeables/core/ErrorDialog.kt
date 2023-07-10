package com.pstorli.pokerdice.ui.composeables.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ErrorDialog(message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text (text = "Error")

            },
            text = {
                Text (message)
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}