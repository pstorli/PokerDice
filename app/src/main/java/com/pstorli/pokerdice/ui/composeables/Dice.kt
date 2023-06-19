package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.conditional
import com.pstorli.pokerdice.domain.model.Die
import com.pstorli.pokerdice.util.Consts.BORDER_DEFAULT_WIDTH_VAL

/**
 * Create a dice composeable.
 */
@Composable
fun Dice (die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null) {
    Dice(die, backColor, borderColor, sizeDp, null)
}

/**
 * Create a dice composeable.
 */
@Composable
fun Dice (die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?) {
    Image (
        painterResource     (die.resId()),
        contentDescription  = stringResource(R.string.dice_image, die.toString()),
        contentScale        = ContentScale.Crop,
        modifier            = Modifier
            .padding(4.dp)
            .background(backColor)
            .border (
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
