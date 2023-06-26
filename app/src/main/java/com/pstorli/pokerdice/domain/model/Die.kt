package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.util.Consts.SUIT_NONE
import com.pstorli.pokerdice.util.Consts.NO_TEXT

/**
 * This class represents a die from 0 - 6
 */
data class Die (var rank: Int=SUIT_NONE, var suit: Int=SUIT_NONE, var name: String=NO_TEXT, var held: Boolean=false, var selected: Boolean=false)
{}