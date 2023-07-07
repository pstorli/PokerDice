package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.getHandName
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_DICE_DP
import com.pstorli.pokerdice.util.Consts.NO_TEXT
import com.pstorli.pokerdice.util.Consts.ZERO
import com.pstorli.pokerdice.util.Consts.removeNewLines

@Composable
fun HandToBeat (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateHandToBeat.value

    var handToBeatValue    = ZERO
    var handToBeatRealName: String = NO_TEXT
    var handToBeatTitle    = stringResource (id = R.string.hand_to_beat_none)

    // Something in that hand?
    if (pokerViewModel.game.hasValue(pokerViewModel.handToBeat.value)) {
        handToBeatValue    = pokerViewModel.scoreHandToBeat()
        handToBeatRealName = LocalContext.current.getHandName(handToBeatValue)

        if (ZERO == handToBeatValue) {
            handToBeatValue = pokerViewModel.pokerScorer.highest(pokerViewModel.handToBeat.value)
        }

        val handToBeatName = removeNewLines (handToBeatRealName) + " (" + handToBeatValue.toString() + ")"

        handToBeatTitle = stringResource (id = R.string.hand_to_beat, handToBeatName)
    }

    LabeledRow(
        title       = handToBeatTitle,
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true
    ) {
        // Loop thru hand to beat.
        for (die in pokerViewModel.handToBeat.value) {
            // Dice -> die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?
            Dice (die, sizeDp=HAND_TO_BEAT_DICE_DP)
        }
    }
}