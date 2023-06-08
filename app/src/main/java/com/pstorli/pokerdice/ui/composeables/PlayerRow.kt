package com.pstorli.pokerdice.ui.composeables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.debug
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts

@Composable
fun PlayerRow (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.player),
        titleColor  = Consts.color(Consts.COLOR_TEXT_NAME, LocalContext.current)
    ) {
        // Roll Dice
        PokerButton(
            LocalContext.current.resources.getString(R.string.roll_dice),
            Consts.color(Consts.COLOR_ROLL_DICE_NAME, LocalContext.current),
            {
                "Roll dice pressed.".debug()

                // They clicked the button.
                pokerViewModel.onEvent(PokerEvent.RollEvent)
            })

        // Cash Out
        PokerButton(
            LocalContext.current.resources.getString(R.string.cash_out),
            Consts.color(Consts.COLOR_CASH_OUT_NAME, LocalContext.current),
            {
                "Cash out pressed.".debug()

                // They clicked the button.
                pokerViewModel.onEvent(PokerEvent.CashOutEvent)
            })

        // Cash
        OutlinedTextField(text = pokerViewModel.cash.toString(), color = LocalContext.current.color(Consts.COLOR_CASH_NAME))
    }
}