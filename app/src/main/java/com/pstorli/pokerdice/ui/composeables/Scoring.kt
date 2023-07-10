package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.SCORING_TEXT_FONT_SIZE_SP

@Composable
fun Scoring () {
    Column(
        modifier = Modifier.background(colorScheme.background)
    ) {
        LabeledRow(
            title = stringResource(id = R.string.scoring_title),
            titleColor = LocalContext.current.color(Colors.Title),
            maxWidth = true,
            maxHeight = true
        ) {
            Text (
                text     = stringResource(id = R.string.scoring_text),
                color    = LocalContext.current.color(Colors.Text),
                fontSize = SCORING_TEXT_FONT_SIZE_SP,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }
    }
}