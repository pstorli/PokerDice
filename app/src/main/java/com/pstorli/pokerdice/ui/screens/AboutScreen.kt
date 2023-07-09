package com.pstorli.pokerdice.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerEvent
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.PokerButton
import com.pstorli.pokerdice.ui.composeables.core.LabeledCol
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.ABOUT_SPACER_WIDTH_DP
import com.pstorli.pokerdice.util.Consts.NO_TEXT

/**
 * Show loading poker screen.
 */
@Composable
fun AboutScreen (pokerViewModel: PokerViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LabeledCol(
            title       = stringResource(id = R.string.state_about),
            titleColor  = LocalContext.current.color(Colors.Title),
            maxWidth    = true,
            maxHeight   = true
        ) {
            // Poker Dice logo.
            Row(
                modifier                = Modifier.fillMaxWidth(),
                horizontalArrangement   = Arrangement.Center,
                verticalAlignment       = Alignment.Top

            ) {
                Image(
                    painterResource(R.drawable.ic_launcher),
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.Crop,
                )
            }

            // Show instructions.
            Text (stringResource(id = R.string.about_text))

            Row(
                modifier                = Modifier.fillMaxWidth(),
                horizontalArrangement   = Arrangement.Center,
                verticalAlignment       = Alignment.Top

            ) {
                LabeledRow(
                    title       = NO_TEXT,
                    titleColor  = LocalContext.current.color(Colors.Title)
                ) {
                    val localUriHandler = LocalUriHandler.current
                    val clickText = stringResource(id = R.string.portfolio_click)
                    ClickableText(
                        text = AnnotatedString(clickText),
                        style = TextStyle(
                            fontSize = Consts.URL_FONT_SIZE_SP,
                            color = LocalContext.current.color(Colors.Wild)
                        )
                    ) {
                        localUriHandler.openUri(Consts.URL_STORLI_DESIGNS)
                    }
                }

                // A bit of space between the two.
                Spacer(modifier = Modifier.width(ABOUT_SPACER_WIDTH_DP))

                // Close
                PokerButton(
                    name = LocalContext.current.resources.getString(R.string.close),
                    textColor = LocalContext.current.color(Colors.Btn),
                    onClick = {
                        // They clicked the ok button.
                        pokerViewModel.onEvent(PokerEvent.AboutCloseEvent)
                    })
            }
        }
    }
}