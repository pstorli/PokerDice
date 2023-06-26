package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.Die
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_DICE_SIZE
import com.pstorli.pokerdice.util.Consts.SUIT_CLUB
import com.pstorli.pokerdice.util.Consts.SUIT_DIAMOND
import com.pstorli.pokerdice.util.Consts.SUIT_HEART
import com.pstorli.pokerdice.util.Consts.SUIT_SPADE

@Composable
fun Suits (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateHandToBeat.value

    LabeledRow(
        title       = stringResource(id = R.string.suits),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true
    ) {
        Suit (Die(suit= SUIT_HEART),    sizeDp=HAND_TO_BEAT_DICE_SIZE)
        Suit (Die(suit= SUIT_DIAMOND),  sizeDp=HAND_TO_BEAT_DICE_SIZE)
        Suit (Die(suit= SUIT_CLUB),     sizeDp=HAND_TO_BEAT_DICE_SIZE)
        Suit (Die(suit= SUIT_SPADE),    sizeDp=HAND_TO_BEAT_DICE_SIZE)
    }
}