package com.pstorli.pokerdice.domain.repo.dao

import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE0
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE6
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE0
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE6
import com.pstorli.pokerdice.util.Consts.HUNDRED_VAL
import com.pstorli.pokerdice.util.Consts.ZERO_VAL
import com.pstorli.pokerdice.domain.model.MutablePair

data class PokerDAO (val startCash: Int = HUNDRED_VAL) {
    var cash               = startCash

    // The dice colors. MutablePair with light getColor and dark getColor.
    var colorDice0         = (MutablePair (COLOR_LT_DICE0, COLOR_DK_DICE0))
    var colorDice1         = (MutablePair (COLOR_LT_DICE1, COLOR_DK_DICE1))
    var colorDice2         = (MutablePair (COLOR_LT_DICE2, COLOR_DK_DICE2))
    var colorDice3         = (MutablePair (COLOR_LT_DICE3, COLOR_DK_DICE3))
    var colorDice4         = (MutablePair (COLOR_LT_DICE4, COLOR_DK_DICE4))
    var colorDice5         = (MutablePair (COLOR_LT_DICE5, COLOR_DK_DICE5))
    var colorDice6         = (MutablePair (COLOR_LT_DICE6, COLOR_DK_DICE6))
    var level              = ZERO_VAL
}
