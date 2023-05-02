package com.pstorli.pokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.theme.PokerDiceTheme
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts.DEFAULT_ELEVATION
import com.pstorli.pokerdice.util.Consts.DISABLED_ELEVATION
import com.pstorli.pokerdice.util.Consts.PRESSED_ELEVATION

class MainActivity : ComponentActivity() {
    // The one and only!
    private val pokerViewModel: PokerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        PokerDiceTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color    = MaterialTheme.colorScheme.background
            ) {
                Greeting(pokerViewModel.cash.toString())
            }
        }
    }

    @Composable
    @Suppress("UNUSED_PARAMETER")
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Button(
            onClick = {
                pokerViewModel.onEvent(PokerEvent.RollEvent)
            },
            border = BorderStroke(1.dp, pokerViewModel.btnBorderColor),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = pokerViewModel.btnBorderColor),
            elevation =  ButtonDefaults.buttonElevation(
                defaultElevation  = DEFAULT_ELEVATION,
                pressedElevation  = PRESSED_ELEVATION,
                disabledElevation = DISABLED_ELEVATION)
            )
            {
            Text(text = "Cash $name", color = pokerViewModel.textColor)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PokerDiceTheme {
            Greeting("Preview")
        }
    }
}