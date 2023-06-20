package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.debug
import com.pstorli.pokerdice.domain.model.PokerEvent
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.PokerButton
import com.pstorli.pokerdice.ui.theme.Colors

/**
 * Show loading poker screen.
 */
@Composable
fun SettingsScreen (pokerViewModel: PokerViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.state_settings))

        Row () {
            // Save
            PokerButton(
                name = LocalContext.current.resources.getString(R.string.save),
                textColor = LocalContext.current.color(Colors.Btn),
                onClick = {
                    "Save pressed.".debug()

                    // They clicked the button.
                    pokerViewModel.onEvent(PokerEvent.SaveEvent)
                })

            // Reset
            PokerButton(
                name = LocalContext.current.resources.getString(R.string.reset),
                textColor = LocalContext.current.color(Colors.Wild),
                onClick = {
                    "Reset pressed.".debug()

                    // They clicked the button.
                    pokerViewModel.onEvent(PokerEvent.ResetEvent)
                })

            // Cancel
            PokerButton(
                name = LocalContext.current.resources.getString(R.string.cancel),
                textColor = LocalContext.current.color(Colors.Btn),
                onClick = {
                    "Cancel pressed.".debug()

                    // They clicked the button.
                    pokerViewModel.onEvent(PokerEvent.CancelEvent)
                })
        }
    }
}