package com.pstorli.pokerdice.ui.screens

import androidx.compose.runtime.Composable
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.PlayerRow

/**
 * Show initial poker screen.
 */
@Composable
fun PokerScreenLoaded (pokerViewModel: PokerViewModel) {
    PlayerRow (pokerViewModel)
}