package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.util.Consts.DICE_FIVE
import com.pstorli.pokerdice.util.Consts.DICE_FOUR
import com.pstorli.pokerdice.util.Consts.DICE_ONE
import com.pstorli.pokerdice.util.Consts.DICE_SIX
import com.pstorli.pokerdice.util.Consts.DICE_THREE
import com.pstorli.pokerdice.util.Consts.DICE_TWO
import com.pstorli.pokerdice.util.Consts.DICE_ZERO
import com.pstorli.pokerdice.util.Consts.NO_TEXT

/**
 * This class represents a die from 0 - 6
 */
data class Die (var num: Int=DICE_ZERO, var name: String=NO_TEXT, var held: Boolean=false, var selected: Boolean=false) {

    /**
     * get the resource id for this dice.
     */
    fun resId (): Int {
        var result = R.drawable.zero
        when (num) {
            DICE_ZERO   -> result = R.drawable.zero
            DICE_ONE    -> result = R.drawable.one
            DICE_TWO    -> result = R.drawable.two
            DICE_THREE  -> result = R.drawable.three
            DICE_FOUR   -> result = R.drawable.four
            DICE_FIVE   -> result = R.drawable.five
            DICE_SIX    -> result = R.drawable.six
        }
        return result
    }
}