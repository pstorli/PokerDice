package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.domain.model.PokerEvent
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.Board
import com.pstorli.pokerdice.ui.composeables.HandToBeat
import com.pstorli.pokerdice.ui.composeables.Instructions
import com.pstorli.pokerdice.ui.composeables.PlayerRow
import com.pstorli.pokerdice.ui.composeables.SideDrawer
import com.pstorli.pokerdice.ui.composeables.Title
import com.pstorli.pokerdice.ui.composeables.core.AlertDialog
import com.pstorli.pokerdice.ui.composeables.core.PokerBar
import com.pstorli.pokerdice.util.Consts.CASH_INITIAL
import com.pstorli.pokerdice.util.Consts.NO_TEXT

/**
 * Show initial poker screen.
 */
@Composable
fun MainScreen (pokerViewModel: PokerViewModel) {
    // Side by side rows the same height
    // make these two rows, the children, the same height.
    /*Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        PlayerRow  (pokerViewModel)
        HandToBeat (pokerViewModel)
    }*/
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost  = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                // custom snackbar with the custom border and bc color
                PokerBar (data)
            }
        },
        drawerContent = { SideDrawer() }) { padding ->
        Column(
            modifier = Modifier
                .background(colorScheme.background)
                .padding(padding)
        ) {
            Title ()
            PlayerRow(pokerViewModel)
            Board(pokerViewModel)
            HandToBeat(pokerViewModel)
            Instructions(pokerViewModel)
        }

        // Show a snack bar?
        if (pokerViewModel.snackBarText.isNotEmpty()) {
            val text: String = pokerViewModel.snackBarText
            pokerViewModel.snackBarText = NO_TEXT

            LaunchedEffect(text) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = text,
                    duration = SnackbarDuration.Indefinite)
            }
        }

        // Do they need a loan?
        // Need to be in Start state and outta cash.
        else if (PokerViewModel.PokerUIState.Start == pokerViewModel.getState()
            && pokerViewModel.outaCash
            && pokerViewModel.cash<CASH_INITIAL/3) {
            AlertDialog(text = stringResource(id = R.string.low_on_cash),
            {
                pokerViewModel.onEvent (PokerEvent.AddCashEvent)
            })
        }
    }
}