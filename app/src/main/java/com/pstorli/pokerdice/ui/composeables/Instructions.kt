package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledCol
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.URL_FONT_SIZE_SP
import com.pstorli.pokerdice.util.Consts.URL_WON_FREE_STUFF

@Composable
fun Instructions (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateInstructions.value

    LabeledCol(
        title       = stringResource(id = R.string.instructions),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true,
        maxHeight   = true
    ) {
        // Show instructions.
        Text (pokerViewModel.getInstructions())

        // Add text about free games
        if (pokerViewModel.freeGameOffer()) {
            val localUriHandler = LocalUriHandler.current
            val clickText = stringResource(id = R.string.won_level_prize, pokerViewModel.level)
            ClickableText (text = AnnotatedString (clickText), style = TextStyle(fontSize = URL_FONT_SIZE_SP, color = LocalContext.current.color(Colors.Wild))) {
                localUriHandler.openUri (URL_WON_FREE_STUFF)
            }
        }
    }
}