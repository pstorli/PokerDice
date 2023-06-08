package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.isEdgeSquare

@Composable
fun Board (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.board),
        titleColor  = Consts.color(Consts.COLOR_TEXT_NAME, LocalContext.current),
        maxWidth    = true
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(BOARD_SIZE)) {
            items(pokerViewModel.board.value.size) { index ->
                // Get the dice associated with this square.
                val dice  = pokerViewModel.board.value.get(index)

                // Color will be either edge color or dice color?
                val color = if (isEdgeSquare (index)) Consts.color(Consts.COLOR_EDGE_NAME, LocalContext.current) else pokerViewModel.getColor(dice)

                // Now create the dice.
                createDice(dice, color, pokerViewModel)
            }
        }
    }
}