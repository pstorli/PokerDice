package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_DICE_SIZE

@Composable
fun HandToBeat (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.hand_to_beat),
        titleColor  = LocalContext.current.color(Consts.COLOR_TEXT_NAME),
        maxWidth    = true
    ) {
        // Loop thru hand to beat.
        for (dice in pokerViewModel.handToBeat.value) {
            createDice (dice, pokerViewModel.getColor(dice),HAND_TO_BEAT_DICE_SIZE)
        }
    }
}