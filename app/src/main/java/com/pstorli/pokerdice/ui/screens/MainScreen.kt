package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.Board
import com.pstorli.pokerdice.ui.composeables.HandToBeat
import com.pstorli.pokerdice.ui.composeables.Instructions
import com.pstorli.pokerdice.ui.composeables.PlayerRow
import com.pstorli.pokerdice.ui.composeables.Scoring
import com.pstorli.pokerdice.ui.composeables.Suits
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
    Scaffold(scaffoldState = scaffoldState, drawerContent={Scoring()}) {padding->
        Column(modifier=Modifier.padding(padding)) {
            PlayerRow(pokerViewModel)
            Board(pokerViewModel)
            Instructions(pokerViewModel)
            HandToBeat(pokerViewModel)
            Suits(pokerViewModel)
        }

        if (pokerViewModel.snackBarText.isNotEmpty()) {
            LaunchedEffect(pokerViewModel.snackBarText) {
                scaffoldState.snackbarHostState.showSnackbar (pokerViewModel.snackBarText)
                pokerViewModel.snackBarText = NO_TEXT
            }
        }
    }
}