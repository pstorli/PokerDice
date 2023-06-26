package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.conditional
import com.pstorli.pokerdice.domain.model.Die
import com.pstorli.pokerdice.suitId
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BORDER_DEFAULT_WIDTH_VAL
import com.pstorli.pokerdice.util.Consts.DIAMOND_MIN_WIDTH
import com.pstorli.pokerdice.util.Consts.NO_TEXT

/**
 * Create a dice composeable.
 */
@Composable
fun Suit (die: Die, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null) {
    Suit(die, borderColor, sizeDp, null)
}

/**
 * Create a dice composeable.
 */
@Composable
fun Suit (die: Die, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?) {
    var name     = NO_TEXT
    var min: Dp? = null
    when (die.suit) {
        Consts.SUIT_NONE    -> name = NO_TEXT
        Consts.SUIT_HEART   -> name = stringResource(id = R.string.suit_heart)
        Consts.SUIT_DIAMOND -> {
            name = stringResource(id = R.string.suit_diamond)
            min  = DIAMOND_MIN_WIDTH
        }
        Consts.SUIT_CLUB    -> name = stringResource(id = R.string.suit_club)
        Consts.SUIT_SPADE   -> name = stringResource(id = R.string.suit_spade)
    }

    LabeledRow(
        title       = name,
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = false,
        minWidth    = min,
    ) {
        Image(
            painterResource(die.suitId()),
            contentDescription = stringResource(R.string.dice_image, die.toString()),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(4.dp)
                .background(die.color(isSystemInDarkTheme()))
                .border(
                    BorderStroke(BORDER_DEFAULT_WIDTH_VAL, borderColor),
                    RectangleShape
                )
                .conditional(sizeDp != null) {
                    size(sizeDp!!)
                }
                .conditional(onClick != null) {
                    clickable {
                        onClick?.let { it() }
                    }
                }
        )
    }
}
