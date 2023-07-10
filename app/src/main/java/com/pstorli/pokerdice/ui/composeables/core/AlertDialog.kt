package com.pstorli.pokerdice.ui.composeables.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialog (text: String="", title: String="", btnOKText: String="OK", btnCancelText: String = "Cancel", clicked: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {

        val TEXT_FONT_SIZE_SP               = 18.sp
        val TITLE_FONT_SIZE_SP              = 28.sp

        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            title = {
                Text (text = title, fontSize = TITLE_FONT_SIZE_SP)
            },
            text = {
                Text (text = text, fontSize = TEXT_FONT_SIZE_SP)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        clicked ()
                    }
                ) {
                    Text (btnOKText, fontSize = TEXT_FONT_SIZE_SP)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text (btnCancelText, fontSize = TEXT_FONT_SIZE_SP)
                }
            }
        )
    }
}