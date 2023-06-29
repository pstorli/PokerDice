package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors

@Composable
fun Scoring (pokerViewModel: PokerViewModel) {
    // This will detect any changes to the board edge and recompose your composable.
    pokerViewModel.onUpdateScoring.value

    LabeledRow(
        title       = stringResource(id = R.string.scoring_title),
        titleColor  = LocalContext.current.color(Colors.Title),
        maxWidth    = true,
        maxHeight   = true
    ) {
        Text (stringResource(id = R.string.scoring_text), color = LocalContext.current.color (Colors.Text), modifier = Modifier.verticalScroll(rememberScrollState()))
    }
}