package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
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
        titleColor  = LocalContext.current.color(Consts.COLOR_TEXT_NAME),
        maxWidth    = true
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(BOARD_SIZE)) {
            items(pokerViewModel.board.size) { index ->
                // Treat edge squares differently than dice squares.
                if (isEdgeSquare (index)) {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoardEdge.value

                    // Color will be either edge color or dice color?
                    val color = LocalContext.current.color(Consts.COLOR_EDGE_NAME)

                    // Use PokerButton for edge squares.
                    PokerButton(
                        name        = pokerViewModel.boardText[index],
                        textColor   = pokerViewModel.boardTextColor[index],
                        borderColor = pokerViewModel.boardBorderColor[index],
                        onClick     = {
                            debug ("They clicked on the edge $index.")

                            // They clicked the button on a board square.
                            pokerViewModel.onEvent(PokerEvent.BoardClickEvent (index))
                        })
                }
                else {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoard.value

                    // Get the dice associated with this square.
                    val dice = pokerViewModel.board.get(index)

                    // Now create the dice.
                    createDice(dice, pokerViewModel.getColor(dice), pokerViewModel.boardBorderColor[index])
                }
            }
        }
    }
}