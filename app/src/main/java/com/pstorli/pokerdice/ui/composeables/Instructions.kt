package com.pstorli.pokerdice.ui.composeables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors

@Composable
fun Instructions (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateInstructions.value

    LabeledRow(
        title       = stringResource(id = R.string.instructions),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true,
        maxHeight   = true
    ) {
        Text (pokerViewModel.getInstructions())
    }
}