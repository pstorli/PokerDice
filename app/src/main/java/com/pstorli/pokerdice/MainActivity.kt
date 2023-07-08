package com.pstorli.pokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.ErrorDialog
import com.pstorli.pokerdice.ui.screens.LoadingScreen
import com.pstorli.pokerdice.ui.screens.MainScreen
import com.pstorli.pokerdice.ui.screens.AboutScreen
import com.pstorli.pokerdice.ui.theme.PokerTheme

class MainActivity : ComponentActivity() {
    // The one and only!
    val pokerViewModel: PokerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PokerTheme {

                // A surface container using the 'background' getColor from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = colorScheme.background
                )
                {
                    ShowMainScreen(pokerViewModel)
                }
            }
        }
    }

    /**
     * Based on the current UI state, decide what to show.
     */
    @Composable
    fun ShowMainScreen(pokerViewModel: PokerViewModel) {
        Row(
            modifier                = Modifier.fillMaxSize(),
            horizontalArrangement   = Arrangement.Start,
            verticalAlignment       = Alignment.Top

        ) {
            when (val state = pokerViewModel.uiState.collectAsState().value) {
                // Loading.
                is PokerViewModel.PokerUIState.Loading  ->
                    LoadingScreen ()

                // Error.
                is PokerViewModel.PokerUIState.Error    ->
                    ErrorDialog(state.message)

                // Loading.
                is PokerViewModel.PokerUIState.Start   ->
                    MainScreen (pokerViewModel)

                // Rolling.
                is PokerViewModel.PokerUIState.Rolling   ->
                    MainScreen (pokerViewModel)

                // Settings.
                is PokerViewModel.PokerUIState.About   ->
                    AboutScreen (pokerViewModel)
            }
        }
    }
}