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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.ErrorDialog
import com.pstorli.pokerdice.ui.screens.LoadingScreen
import com.pstorli.pokerdice.ui.screens.PokerScreenLoaded
import com.pstorli.pokerdice.ui.theme.PokerDiceTheme

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

    /**
     * Based on the current UI state, decide what to show.
     */
    @Composable
    fun MainScreen() {
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
                is PokerViewModel.PokerUIState.Loaded   ->
                    PokerScreenLoaded (state.data)
            }
        }
    }
}