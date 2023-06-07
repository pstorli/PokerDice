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
    Column() {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }
}