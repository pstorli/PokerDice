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
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE

@Composable
fun Board (pokerViewModel: PokerViewModel) {
    LabeledRow(
        title       = stringResource(id = R.string.board),
        titleColor  = LocalContext.current.color (Colors.Title),
        maxWidth    = true
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(BOARD_SIZE)) {
            items(pokerViewModel.game.size()) { index ->
                // Get the dice associated with this square.
                var die = pokerViewModel.game.getDie (index)

                // Figure out border getColor to use.
                var color = LocalContext.current.color (Colors.Text)
                var bc    = LocalContext.current.color (Colors.Border)
                if (die.held) {
                    bc    = LocalContext.current.color (Colors.Held)
                    color = bc
                }
                else if (die.selected) {
                    bc = LocalContext.current.color (Colors.Selected)
                    color = bc
                }

                // Treat edge squares differently than dice squares.
                if (pokerViewModel.game.isEdgeSquare (index)) {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoardEdge.value

                    // Use PokerButton for edge squares.
                    PokerButton(
                        name        = die.name,
                        textColor   = color,
                        borderColor = bc,
                        onClick     = {
                            // Do a different sound if de-selecting the square.
                            die = pokerViewModel.game.getDie (index)
                            if (die.selected) {
                                Consts.playSound(R.raw.bet_not, pokerViewModel.getApplication())
                            }
                            else {
                                Consts.playSound(R.raw.bet, pokerViewModel.getApplication())
                            }

                            // They clicked the button on a board square.
                            pokerViewModel.onEvent(PokerEvent.EdgeClickEvent (index))
                        })
                }
                else {
                    // This will detect any changes to the board edge and recompose your composable.
                    pokerViewModel.onUpdateBoard.value

                    // Now create the dice.
                    // Die  -> var num: Int=DICE_SUIT_NONE, var name: String=NO_TEXT, var held: Boolean=false, var selected: Boolean=false
                    // Dice -> die: Die, backColor: Color=MaterialTheme.colorScheme.background, borderColor: Color=MaterialTheme.colorScheme.outline, sizeDp: Dp?=null, onClick: (() -> Unit?)?
                    Dice (die, bc, onClick =
                    {
                        // They clicked the button on a board square.
                        pokerViewModel.onEvent(PokerEvent.BoardClickEvent (index))
                    })
                }
            }
        }
    }
}