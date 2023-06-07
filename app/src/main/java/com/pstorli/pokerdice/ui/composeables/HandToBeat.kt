package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.resId
import com.pstorli.pokerdice.util.Consts

@Composable
fun HandToBeat (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title = stringResource(id = R.string.hand_to_beat),
        Consts.color(Consts.COLOR_TEXT_NAME, LocalContext.current)
    ) {
        // Loop thru hand to beat.
        for (item in pokerViewModel.handToBeat.value) {
            Image (
                painterResource(item.resId()),
                contentDescription  = stringResource(R.string.dice_image, item.name),
                contentScale        = ContentScale.Crop,
                modifier            = Modifier.padding(4.dp)
            )
        }
    }
}