package com.pstorli.pokerdice.domain.repo.dao

import com.pstorli.pokerdice.ui.theme.COLOR_DK_SUIT_NONE
import com.pstorli.pokerdice.ui.theme.COLOR_DK_SUIT_HEART
import com.pstorli.pokerdice.ui.theme.COLOR_DK_SUIT_DIAMOND
import com.pstorli.pokerdice.ui.theme.COLOR_DK_SUIT_CLUB
import com.pstorli.pokerdice.ui.theme.COLOR_DK_SUIT_SPADE
import com.pstorli.pokerdice.ui.theme.COLOR_LT_SUIT_NONE
import com.pstorli.pokerdice.ui.theme.COLOR_LT_SUIT_HEART
import com.pstorli.pokerdice.ui.theme.COLOR_LT_SUIT_DIAMOND
import com.pstorli.pokerdice.ui.theme.COLOR_LT_SUIT_CLUB
import com.pstorli.pokerdice.ui.theme.COLOR_LT_SUIT_SPADE
import com.pstorli.pokerdice.util.Consts.HUNDRED_VAL
import com.pstorli.pokerdice.util.Consts.SUIT_NONE_VAL
import com.pstorli.pokerdice.domain.model.MutablePair

data class PokerDAO (val startCash: Int = HUNDRED_VAL) {
    var cash               = startCash

    // The dice colors. MutablePair with light getColor and dark getColor.
    var color_suit_none    = (MutablePair (COLOR_LT_SUIT_NONE, COLOR_DK_SUIT_NONE))
    var color_suit_heart   = (MutablePair (COLOR_LT_SUIT_HEART, COLOR_DK_SUIT_HEART))
    var color_suit_diamond = (MutablePair (COLOR_LT_SUIT_DIAMOND, COLOR_DK_SUIT_DIAMOND))
    var color_suit_club    = (MutablePair (COLOR_LT_SUIT_CLUB, COLOR_DK_SUIT_CLUB))
    var color_suit_spade   = (MutablePair (COLOR_LT_SUIT_SPADE, COLOR_DK_SUIT_SPADE))
    var level              = SUIT_NONE_VAL
}
