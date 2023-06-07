package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.HandToBeat
import com.pstorli.pokerdice.ui.composeables.PlayerRow

/**
 * Show initial poker screen.
 */
@Composable
fun PokerScreenLoaded (pokerViewModel: PokerViewModel) {
    // make these two rows, the children, the same height.
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }
}