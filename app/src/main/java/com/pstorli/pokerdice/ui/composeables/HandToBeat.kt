package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.getHandToBeatName
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_DICE_DP
import com.pstorli.pokerdice.util.Consts.NOTHING
import com.pstorli.pokerdice.util.Consts.removeNewLines

@Composable
fun HandToBeat (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateHandToBeat.value

    val handToBeatValue = pokerViewModel.scoreHandToBeat()
    val handToBeatRealName: String
    if (handToBeatValue > NOTHING) {
        handToBeatRealName  = LocalContext.current.getHandToBeatName (handToBeatValue)
    }
    else {
        handToBeatRealName  = LocalContext.current.resources.getString(R.string.nothing)
    }

    val handToBeatName  = handToBeatValue.toString() + ' ' + removeNewLines (handToBeatRealName)
    val handToBeatTitle = stringResource (id = R.string.hand_to_beat, handToBeatName)

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