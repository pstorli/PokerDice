package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_DICE_SIZE

@Composable
fun HandToBeat (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateHandToBeat.value

    LabeledRow(
        title       = stringResource(id = R.string.hand_to_beat),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true
    ) {
        // Loop thru hand to beat.
        for (die in pokerViewModel.handToBeat.value) {
            // Dice -> die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?
            Dice (die, backColor=LocalContext.current.color (die), sizeDp=HAND_TO_BEAT_DICE_SIZE)
        }
    }
}