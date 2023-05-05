package com.pstorli.pokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.component.PokerButton
import com.pstorli.pokerdice.ui.theme.PokerDiceTheme
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts.PADDING_DEFAULT

class MainActivity : ComponentActivity() {
    // The one and only!
    private val pokerViewModel: PokerViewModel by viewModels()

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
        Row (
            modifier                = Modifier.fillMaxSize(),
            horizontalArrangement   = Arrangement.Start,
            verticalAlignment       = Alignment.Top

        ) {
            PokerButton (
                pokerViewModel.cash.toString(),
                {
                    // They clicked the buttopn.
                    pokerViewModel.onEvent (PokerEvent.RollEvent)
                })

            Text(
                modifier = Modifier.padding(horizontal = PADDING_DEFAULT),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline
            )
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PokerDiceTheme {
            MainScreen()
        }
    }
}