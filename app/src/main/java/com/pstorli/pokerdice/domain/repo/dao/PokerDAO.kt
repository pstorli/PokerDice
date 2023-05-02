package com.pstorli.pokerdice.domain.repo.dao

import com.pstorli.pokerdice.ui.theme.COLOR_BET_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_DICE6
import com.pstorli.pokerdice.ui.theme.COLOR_HOLD_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_TEXT
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.HUNDRED
import com.pstorli.pokerdice.util.Consts.ZERO

data class PokerDAO (val betInitial: Int = 0) {
    var bet                = betInitial
    var board              = Array(Consts.BOARD_SIZE) { IntArray(Consts.BOARD_SIZE) }
    var cash               = HUNDRED
    var colorBetBorder     = COLOR_BET_BORDER
    var colorBorder        = COLOR_BORDER
    var colorDice1         = COLOR_DICE1
    var colorDice2         = COLOR_DICE2
    var colorDice3         = COLOR_DICE3
    var colorDice4         = COLOR_DICE4
    var colorDice5         = COLOR_DICE5
    var colorDice6         = COLOR_DICE6
    var colorHoldBorder    = COLOR_HOLD_BORDER
    var colorText          = COLOR_TEXT
    var rolls              = ZERO
}
