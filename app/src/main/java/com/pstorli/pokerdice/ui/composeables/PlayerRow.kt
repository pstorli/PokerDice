package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.debug
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.composeables.core.OutlinedTextField
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BET_MIN_WIDTH
import com.pstorli.pokerdice.util.Consts.CASH_MIN_WIDTH
import com.pstorli.pokerdice.util.Consts.ROLLS_MIN_WIDTH

@Composable
fun PlayerRow (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.player),
        titleColor  = LocalContext.current.color(Consts.COLOR_TEXT_NAME),
        maxWidth    = true
    ) {
        // Roll Dice
        PokerButton(
            name        = LocalContext.current.resources.getString(R.string.roll_dice),
            textColor   = LocalContext.current.color(Consts.COLOR_ROLL_DICE_NAME),
            onClick     = {
                "Roll dice pressed.".debug()

                // They clicked the button.
                pokerViewModel.onEvent(PokerEvent.RollEvent)
            })

        // Cash Out
        PokerButton(
            name        = LocalContext.current.resources.getString(R.string.cash_out),
            textColor   = LocalContext.current.color(Consts.COLOR_CASH_OUT_NAME),
            onClick     = {
                "Cash out pressed.".debug()

                // They clicked the button.
                pokerViewModel.onEvent(PokerEvent.CashOutEvent)
            })

        // Cash
        OutlinedTextField(
            text        = LocalContext.current.resources.getString(R.string.cash),
            textColor   = LocalContext.current.color(Consts.COLOR_OUT_TEXT_NAME),
            value       = pokerViewModel.cash.toString(),
            minWidth    = CASH_MIN_WIDTH,
            titleColor  = LocalContext.current.color(Consts.COLOR_CASH_BORDER_NAME))

        // Bet
        OutlinedTextField(
            text        = LocalContext.current.resources.getString(R.string.bet),
            value       = pokerViewModel.bet.toString(),
            textColor   = LocalContext.current.color(Consts.COLOR_OUT_TEXT_NAME),
            minWidth    = BET_MIN_WIDTH,
            titleColor  = LocalContext.current.color(Consts.COLOR_BET_BORDER_NAME))

        // Rolls
        OutlinedTextField(
            text        = LocalContext.current.resources.getString(R.string.rolls),
            value       = pokerViewModel.rolls.toString(),
            textColor   = LocalContext.current.color(Consts.COLOR_OUT_TEXT_NAME),
            minWidth    = ROLLS_MIN_WIDTH,
            titleColor  = LocalContext.current.color(Consts.COLOR_ROLLS_BORDER_NAME))
    }
}