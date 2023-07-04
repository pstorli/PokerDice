package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.Board
import com.pstorli.pokerdice.ui.composeables.HandToBeat
import com.pstorli.pokerdice.ui.composeables.Instructions
import com.pstorli.pokerdice.ui.composeables.PlayerRow
import com.pstorli.pokerdice.ui.composeables.SnackBar
import com.pstorli.pokerdice.ui.composeables.Suits

/**
 * Show initial poker screen.
 */
@Composable
fun MainScreen (pokerViewModel: PokerViewModel) {
    // Side by side rows the same height
    // make these two rows, the children, the same height.
    /*Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }*/

    // Two rows in a column.

    Column () {
        PlayerRow       (pokerViewModel)
        Board           (pokerViewModel)
        HandToBeat      (pokerViewModel)
        Suits           (pokerViewModel)
        Instructions    (pokerViewModel)
        SnackBar        (pokerViewModel)

        Snackbar {
            Text(text = "Hello")
        }
    }
}