package com.pstorli.pokerdice.domain.repo.dao

import com.pstorli.pokerdice.domain.model.Dice
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
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.HUNDRED_VAL
import com.pstorli.pokerdice.util.Consts.ZERO_VAL

data class PokerDAO (val betInitial: Int = 0) {
    var bet                = betInitial
    var board              = Array<Dice>(Consts.BOARD_SIZE * Consts.BOARD_SIZE) { Dice.Zero }
    var cash               = HUNDRED_VAL

    // The dice colors. Pair with light color and dark color.
    var colorDice0         = (Pair (COLOR_LT_DICE0, COLOR_DK_DICE0))
    var colorDice1         = (Pair (COLOR_LT_DICE1, COLOR_DK_DICE1))
    var colorDice2         = (Pair (COLOR_LT_DICE2, COLOR_DK_DICE2))
    var colorDice3         = (Pair (COLOR_LT_DICE3, COLOR_DK_DICE3))
    var colorDice4         = (Pair (COLOR_LT_DICE4, COLOR_DK_DICE4))
    var colorDice5         = (Pair (COLOR_LT_DICE5, COLOR_DK_DICE5))
    var colorDice6         = (Pair (COLOR_LT_DICE6, COLOR_DK_DICE6))
    var rolls              = ZERO_VAL
}
