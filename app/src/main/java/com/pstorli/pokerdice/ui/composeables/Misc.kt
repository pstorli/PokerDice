package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.domain.model.Dice
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.resId
import com.pstorli.pokerdice.util.Consts.DICE_FIVE
import com.pstorli.pokerdice.util.Consts.DICE_FOUR
import com.pstorli.pokerdice.util.Consts.DICE_ONE
import com.pstorli.pokerdice.util.Consts.DICE_SIX
import com.pstorli.pokerdice.util.Consts.DICE_THREE
import com.pstorli.pokerdice.util.Consts.DICE_TWO
import com.pstorli.pokerdice.util.Consts.DICE_ZERO

// *********************************************************************************************
// Jetpack Compose Helper composeable functions
// *********************************************************************************************

/**
 * Create a dice image.
 */
@Composable
fun createDice (num: Int, pokerViewModel: PokerViewModel) {
    when (num) {
        DICE_ZERO   -> createDice (Dice.Zero,   pokerViewModel.getColor(Dice.Zero),     pokerViewModel)
        DICE_ONE    -> createDice (Dice.One,    pokerViewModel.getColor(Dice.One),      pokerViewModel)
        DICE_TWO    -> createDice (Dice.Two,    pokerViewModel.getColor(Dice.Two),      pokerViewModel)
        DICE_THREE  -> createDice (Dice.Three,  pokerViewModel.getColor(Dice.Three),    pokerViewModel)
        DICE_FOUR   -> createDice (Dice.Four,   pokerViewModel.getColor(Dice.Four),     pokerViewModel)
        DICE_FIVE   -> createDice (Dice.Five,   pokerViewModel.getColor(Dice.Five),     pokerViewModel)
        DICE_SIX    -> createDice (Dice.Six,    pokerViewModel.getColor(Dice.Six),      pokerViewModel)
    }
}

/**
 * Create a dice image.
 */
@Composable
fun createDice (dice: Dice, color: Color, pokerViewModel: PokerViewModel) {
    Image (
        painterResource(dice.resId()),
        contentDescription  = stringResource(R.string.dice_image, dice.name),
        contentScale        = ContentScale.Crop,
        modifier            = Modifier.padding(4.dp).background(color)
    )
}