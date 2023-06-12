package com.pstorli.pokerdice.ui.composeables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.util.Consts

@Composable
fun Instructions (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateInstructins.value

    LabeledRow(
        title       = stringResource(id = R.string.instructions),
        titleColor  = LocalContext.current.color(Consts.COLOR_TEXT_NAME),
        maxWidth    = true
    ) {
        Text (pokerViewModel.getInstructions())
    }
}