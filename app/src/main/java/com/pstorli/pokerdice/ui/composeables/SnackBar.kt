package com.pstorli.pokerdice.ui.composeables

import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.util.Consts

@Composable
fun SnackBar (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateSnackbar.value

    // Show a snack bar?
    if (pokerViewModel.snackBarText.length> Consts.ZERO) {
        Toast.makeText(LocalContext.current, pokerViewModel.snackBarText, Toast.LENGTH_LONG).show() // TODO Until snackbar fixed.
        Snackbar {
            Text(text = pokerViewModel.snackBarText)
        }

        // Clear the snackbar text.
        pokerViewModel.snackBarText = Consts.NO_TEXT
    }
}