package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.HandToBeat
import com.pstorli.pokerdice.ui.composeables.PlayerRow

/**
 * Show initial poker screen.
 */
@Composable
fun PokerScreenLoaded (pokerViewModel: PokerViewModel) {
    // Side by sdie rows the same height
    // make these two rows, the children, the same height.
    /*Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }*/

    // Two rows in a column.
    Column() {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }
}