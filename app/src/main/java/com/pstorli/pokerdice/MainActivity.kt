package com.pstorli.pokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.LabeledRow
import com.pstorli.pokerdice.ui.composeables.OutlinedTextField
import com.pstorli.pokerdice.ui.composeables.PokerButton
import com.pstorli.pokerdice.ui.theme.PokerDiceTheme
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts

class MainActivity : ComponentActivity() {
    // The one and only!
    val pokerViewModel: PokerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokerDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                )
                {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top

        ) {
            LabeledRow(
                title = stringResource(id = R.string.player),
                Consts.color(Consts.COLOR_TEXT_NAME, LocalContext.current)
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
    }
}