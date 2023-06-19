package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.domain.model.PokerViewModel
import com.pstorli.pokerdice.ui.composeables.core.LabeledRow
import com.pstorli.pokerdice.domain.model.PokerEvent
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.debug
import com.pstorli.pokerdice.util.Consts.isEdgeSquare

@Composable
fun Board (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.board),
        titleColor  = LocalContext.current.color (Colors.Title),
        maxWidth    = true
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(BOARD_SIZE)) {
            items(pokerViewModel.board.size) { index ->
                // Get the dice associated with this square.
                val die = pokerViewModel.board.get(index)

                // Figure out border getColor to use.
                var bc = LocalContext.current.color (Colors.Border)
                if (die.held) {
                    bc = LocalContext.current.color (Colors.Held)
                }
                else if (die.selected) {
                    bc = LocalContext.current.color (Colors.Selected)
                }

                // Treat edge squares differently than dice squares.
                if (isEdgeSquare (index)) {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoardEdge.value

                    // Use PokerButton for edge squares.
                    PokerButton(
                        name        = die.name,
                        textColor   = LocalContext.current.color (Colors.Text),
                        borderColor = bc,
                        onClick     = {
                            debug ("They clicked on the edge $index.")

                            // They clicked the button on a board square.
                            pokerViewModel.onEvent(PokerEvent.EdgeClickEvent (index))
                        })
                }
                else {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoard.value

                    // Now create the dice.
                    // Die  -> var num: Int=DICE_ZERO, var name: String=NO_TEXT, var held: Boolean=false, var selected: Boolean=false
                    // Dice -> die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?
                    Dice (die, LocalContext.current.color (die), bc, onClick =
                    {
                        debug ("They clicked on the board at $index.")

                        // They clicked the button on a board square.
                        pokerViewModel.onEvent(PokerEvent.BoardClickEvent (index))
                    })
                }
            }
        }
    }
}