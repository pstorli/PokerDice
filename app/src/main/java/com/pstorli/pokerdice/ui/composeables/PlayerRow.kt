package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.debug
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.composeables.core.OutlinedTextField
import com.pstorli.pokerdice.domain.model.PokerEvent
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.BET_MIN_WIDTH_DP
import com.pstorli.pokerdice.util.Consts.CASH_MIN_WIDTH_DP
import com.pstorli.pokerdice.util.Consts.LEVEL_MIN_WIDTH_DP
import com.pstorli.pokerdice.util.Consts.ROLLS_MIN_WIDTH_DP
import com.pstorli.pokerdice.util.Consts.WON_MIN_WIDTH_DP

@Composable
fun PlayerRow (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdatePlayer.value

    LabeledRow(
        title       = stringResource(id = R.string.player),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true
    ) {
        Column () {
            Row() {
                // Roll Dice
                val rollTextColor = LocalContext.current.color(Colors.Wild)
                var rollTitle = LocalContext.current.resources.getString(R.string.roll_dice)
                if (PokerViewModel.PokerUIState.Start == pokerViewModel.getState()) {
                    rollTitle = LocalContext.current.resources.getString(R.string.start)
                }

                PokerButton(
                    name = rollTitle,
                    textColor = rollTextColor,
                    onClick = {
                        "Roll dice pressed.".debug()

                        // They clicked the button.
                        if (PokerViewModel.PokerUIState.Start == pokerViewModel.getState()) {
                            pokerViewModel.onEvent(PokerEvent.StartEvent)
                        }
                        else {
                            pokerViewModel.onEvent(PokerEvent.RollEvent)
                        }
                    })

                // Cash Out
                if (PokerViewModel.PokerUIState.Rolling == pokerViewModel.uiState.collectAsState().value) {
                    PokerButton(
                        name = LocalContext.current.resources.getString(R.string.cash_out),
                        textColor = LocalContext.current.color(Colors.Btn),
                        onClick = {
                            "Cash out pressed.".debug()

                            // They clicked the button.
                            pokerViewModel.onEvent(PokerEvent.CashOutEvent)
                        })
                }

                // Reset
                PokerButton(
                    name = LocalContext.current.resources.getString(R.string.reset),
                    textColor = LocalContext.current.color(Colors.Btn),
                    onClick = {
                        "Reset pressed.".debug()

                        // They clicked the reset button.
                        pokerViewModel.onEvent(PokerEvent.ResetEvent)
                    })

                // Settings
                if (PokerViewModel.PokerUIState.Start == pokerViewModel.getState()) {
                    PokerButton(
                        name = LocalContext.current.resources.getString(R.string.state_settings),
                        textColor = LocalContext.current.color(Colors.Btn),
                        onClick = {
                            "Settings pressed.".debug()

                            // They clicked the button.
                            pokerViewModel.onEvent(PokerEvent.SettingsEvent)
                        })
                }
            }

            Row() {
                // Cash
                OutlinedTextField(
                    text = LocalContext.current.resources.getString(R.string.cash),
                    textColor = LocalContext.current.color(Colors.Text),
                    value = pokerViewModel.cash.toString(),
                    minWidth = CASH_MIN_WIDTH_DP,
                    titleColor = LocalContext.current.color(Colors.Title)
                )

                // Rolls
                OutlinedTextField(
                    text = LocalContext.current.resources.getString(R.string.rolls),
                    value = pokerViewModel.rolls.toString(),
                    textColor = LocalContext.current.color(Colors.Text),
                    minWidth = ROLLS_MIN_WIDTH_DP,
                    titleColor = LocalContext.current.color(Colors.Title)
                )

                // Bet
                OutlinedTextField(
                    text = LocalContext.current.resources.getString(R.string.bet),
                    value = pokerViewModel.bet.toString(),
                    textColor = LocalContext.current.color(Colors.Text),
                    minWidth = BET_MIN_WIDTH_DP,
                    titleColor = LocalContext.current.color(Colors.Title)
                )

                // Won
                OutlinedTextField(
                    text = LocalContext.current.resources.getString(R.string.won),
                    value = pokerViewModel.won.toString(),
                    textColor = LocalContext.current.color(Colors.Text),
                    minWidth = WON_MIN_WIDTH_DP,
                    titleColor = LocalContext.current.color(Colors.Title)
                )

                // Level
                OutlinedTextField(
                    text = LocalContext.current.resources.getString(R.string.level),
                    value = pokerViewModel.level.toString(),
                    textColor = LocalContext.current.color(Colors.Text),
                    minWidth = LEVEL_MIN_WIDTH_DP,
                    titleColor = LocalContext.current.color(Colors.Title)
                )
            }
        }
    }
}