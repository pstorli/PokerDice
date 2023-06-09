package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.model.PokerViewModel
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.debug
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
                // Treat edge squares differently than dice squares.
                if (isEdgeSquare (index)) {
                    // Color will be either edge color or dice color?
                    val color = Consts.color(Consts.COLOR_EDGE_NAME, LocalContext.current)

                    // Use PokerButton for edge squares.
                    PokerButton(
                        name        = pokerViewModel.getText (index),
                        textColor   = pokerViewModel.getTextColor (index),
                        borderColor = pokerViewModel.getBorderColor (index),
                        onClick     = {
                            debug ("They clicked on the edge $index.")

                            // They clicked the button on a board square.
                            pokerViewModel.onEvent(PokerEvent.BoardClickEvent (index))
                        })
                }
                else {
                    // Get the dice associated with this square.
                    val dice = pokerViewModel.board.value.get(index)

                    // Now create the dice.
                    createDice(dice, pokerViewModel.getColor(dice), pokerViewModel.getBorderColor (index))
                }
            }
        }
    }
}