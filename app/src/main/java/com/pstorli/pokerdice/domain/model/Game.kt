package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.SQUARE_LAST
import com.pstorli.pokerdice.util.Consts.DICE_IN_HAND
import com.pstorli.pokerdice.util.Consts.FIRST_ROW_COL
import com.pstorli.pokerdice.util.Consts.LAST_ROW_COL
import com.pstorli.pokerdice.util.Consts.NO_RANK
import com.pstorli.pokerdice.util.Consts.NUM_SQUARES
import com.pstorli.pokerdice.util.Consts.SQUARE_FIRST
import com.pstorli.pokerdice.util.Consts.SUIT_NONE_VAL
import com.pstorli.pokerdice.util.Consts.ZERO
import com.pstorli.pokerdice.util.Consts.randomRank
import com.pstorli.pokerdice.util.Consts.randomSuit

class Game () {
    // The game board, 7X7 AS A list of 49 Dice items
    var board = Array (NUM_SQUARES) { Die() }

    // *********************************************************************************************
    // More helper functions
    // *********************************************************************************************

    /**
     * Is this a edge square?
     */
    fun isEdgeSquare (index: Int): Boolean {
        return isEdgeSquareTopBottom (index) || isEdgeSquareSide (index)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareTopBottom (index: Int): Boolean {
        val rowCol = rowCol (index)
        return isEdgeSquareTopBottom (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareTopBottom (row: Int, col: Int): Boolean {
        return if (SQUARE_FIRST == row || SQUARE_LAST == row)
            true else false
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareSide (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareSide (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareSide (row: Int, col: Int): Boolean {
        return if (SQUARE_FIRST == col || SQUARE_LAST == col)
            true else false
    }

    /**
     * Is this a corner square?
     */
    fun isCornerSquare (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isCornerSquare (rowCol.first, rowCol.second)
    }

    /**
     * Is this a corner square?
     */
    fun isCornerSquare (row: Int, col: Int): Boolean {
        var result = false
        
        // UL
        if (SQUARE_FIRST == row && SQUARE_FIRST == col) {
            result = true
        }

        // UR
        else if (SQUARE_FIRST == row && SQUARE_LAST == col) {
            result = true
        }

        // LL
        else if (SQUARE_LAST == row && SQUARE_FIRST == col) {
            result = true
        }

        // LR
        else if (SQUARE_LAST == row && SQUARE_LAST == col) {
            result = true
        }

        return result
    }

    /**
     * Deterine the row,col from the index.
     */
    fun row (index: Int): Int {
        return index / Consts.BOARD_SIZE
    }

    /**
     * Deterine the row,col from the index.
     */
    fun col (index: Int): Int {
        return index % Consts.BOARD_SIZE
    }

    /**
     * Deterine the row,col from the index.
     */
    fun rowCol (index: Int): Pair<Int,Int> {
        return Pair (row (index),col (index))
    }

    /**
     * Get the index from a row,col
     */
    fun index (row:Int, col: Int): Int {
        return row * Consts.BOARD_SIZE + col
    }

    /**
     * Get the opposite index from a row,col
     */
    fun opposite (index:Int): Int {
        val rowCol = rowCol(index)
        return opposite (rowCol.first, rowCol.second)
    }

    /**
     * Get the opposite index from a row,col
     */
    fun opposite (row:Int, col: Int): Int {
        var oppRow     = SQUARE_FIRST
        var oppCol     = SQUARE_FIRST
        
        // A corner square?
        if (isCornerSquare(row, col)) {
            oppRow     = SQUARE_LAST - row
            oppCol     = SQUARE_LAST - col
        }

        else if (isEdgeSquareSide (row, col)) {
            oppCol = SQUARE_LAST - col
        }

        else if (isEdgeSquareTopBottom(row, col)) {
            oppRow = SQUARE_LAST - row
        }
        
        return index (oppRow, oppCol)
    }

    /**
     * Given a row/col create a unique square name.
     */
    fun getSquareName (index: Int): String {
        return "Square $index"
    }

    /**
     * Reset the board.
     */
    fun reset () {
        // Reset board.
        for (pos in 0 until NUM_SQUARES) {
            board [pos] = Die (Consts.SUIT_NONE)
        }
    }

    /**
     * Make this square and opposite square have their bet index set.
     */
    fun setBetSquares (index: Int) {
        // What row/col did they click?
        val row     = row(index)
        val col     = col(index)
        val dice    = board [index]
        val select  = !dice.selected

        // ****************************************
        // A corner?
        // ****************************************

        // Lower right or                                Upper left?
        if ((SQUARE_LAST == row && SQUARE_LAST == col) || (SQUARE_FIRST == row && SQUARE_FIRST == col)) {
            // Set upper left and lower right.
            board [index(SQUARE_LAST, SQUARE_LAST)].selected   = select
            board [index(SQUARE_FIRST, SQUARE_FIRST)].selected = select
        }

        // Lower left or                                 Upper right?
        else if ((SQUARE_LAST == row && SQUARE_FIRST == col) || (SQUARE_FIRST == row && SQUARE_LAST == col)) {
            // Set lower left and upper right.
            board [index(SQUARE_LAST, SQUARE_FIRST)].selected  = select
            board [index(SQUARE_FIRST, SQUARE_LAST)].selected  = select
        }

        // ****************************************
        // Straight shot vertically?
        // ****************************************
        else if (SQUARE_LAST == row || SQUARE_FIRST == row) {
            // Set Bottom and top.
            board [index(SQUARE_LAST, col)].selected          = select
            board [index(SQUARE_FIRST, col)].selected         = select
        }

        // ****************************************
        // Straight shot horizontally?
        // ****************************************
        else if (SQUARE_LAST == col || SQUARE_FIRST == col) {
            // Set Left and right.
            board [index(row, SQUARE_LAST)].selected         = select
            board [index(row, SQUARE_FIRST)].selected        = select
        }

        // Something else.
        else {
            board [index].selected = !board [index].selected
        }
    }

    /**
     * Compute the bet.
     */
    fun computeBet (): Int {
        var bets = SUIT_NONE_VAL

        // Top / Bottom
        for (which in SQUARE_FIRST..SQUARE_LAST) {
            if (board [index(SQUARE_FIRST, which)].selected) {
                bets++
            }
            if (board [index(SQUARE_LAST, which)].selected) {
                bets++
            }
        }

        // Left / Right
        for (which in SQUARE_FIRST +1 .. SQUARE_LAST -1) {
            if (board [index(which, SQUARE_FIRST)].selected) {
                bets++
            }
            if (board [index(which, SQUARE_LAST)].selected) {
                bets++
            }
        }

        return bets
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Populate the board and hand.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Populate the board with dice.
     */
    fun populateBoard () {
        // Get some dice.
        for (row in SQUARE_FIRST +1 until SQUARE_LAST) {
            for (col in SQUARE_FIRST + 1 until SQUARE_LAST) {
                val index = index(row, col)
                val die   = board.get(index)

                // If square is not held
                if (!die.held) {
                    board[index] = Die (randomRank(), randomSuit())
                }
            }
        }
    }

    /**
     * Get Diagonal UL->LR squares from board.
     */
    fun getHandDiagonalUpperLeftToLowerRight (): Array<Die> {

        val hand: Array<Die> = Array(DICE_IN_HAND) { Die() }
        var handIndex = ZERO

        // Put some dice into the hand.
        for (pos in FIRST_ROW_COL .. LAST_ROW_COL) {
            if (handIndex<DICE_IN_HAND) {
                hand[handIndex] = board.get (index(pos, pos))
                handIndex++
            }
            else {
                break
            }
        }

        return hand
    }

    /**
     * Get Diagonal UR->LL squares from board.
     */
    fun getHandDiagonalUpperRightToLowerLeft (): Array<Die> {

        val hand: Array<Die> = Array(DICE_IN_HAND) { Die() }
        var handIndex = ZERO

        // Put some dice into the hand.
        for (pos in FIRST_ROW_COL .. LAST_ROW_COL) {
            if (handIndex<DICE_IN_HAND) {
                hand[handIndex] = board.get (index(pos, 1+LAST_ROW_COL-pos))
                handIndex++
            }
            else {
                break
            }
        }

        return hand
    }

    /**
     * Get a column of squares from the board.
     */
    fun getHandByColumn (colIndex: Int): Array<Die> {

        val hand: Array<Die> = Array(DICE_IN_HAND) { Die() }
        var handIndex = ZERO

        // Put some dice into the hand.
        for (pos in FIRST_ROW_COL .. LAST_ROW_COL) {
            if (handIndex<DICE_IN_HAND) {
                hand[handIndex] = board.get(index(pos, colIndex))
                handIndex++
            }
            else {
                break
            }
        }

        return hand
    }

    /**
     * Get a row of squares from the board.
     */
    fun getHandByRow (rowIndex: Int): Array<Die> {

        val hand: Array<Die> = Array(DICE_IN_HAND) { Die() }
        var handIndex = ZERO

        // Put some dice into the hand.
        for (pos in FIRST_ROW_COL .. LAST_ROW_COL) {
            if (handIndex<DICE_IN_HAND) {
                hand[handIndex] = board.get(index(rowIndex, pos))
                handIndex++
            }
            else {
                break
            }
        }

        return hand
    }

    /**
     * Get the hand by the index that was selected.
     */
    fun getHand (index: Int): Array<Die> {
        var hand: Array<Die> = Array(DICE_IN_HAND) { Die() }

        // Ignore if this is not an edge?
        if (isEdgeSquare(index)) {
            if (index>= ZERO && index< NUM_SQUARES) {
                // Get the die
                var die = board [index]

                // This index is on the board.
                val row = row(index)
                val col = col(index)
                //Array (DICE_IN_HAND) { Die() } ()

                // *****************************************************************************
                // Rank these
                // *****************************************************************************

                // Upper Left?
                if (ZERO == row && ZERO == col) {
                    hand = getHandDiagonalUpperLeftToLowerRight ()
                }

                // Lower left?
                else if (LAST_ROW_COL == row && ZERO == col) {
                    hand = getHandDiagonalUpperRightToLowerLeft ()
                }

                // Upper Right?
                else if (ZERO == row && LAST_ROW_COL == col) {
                    hand = getHandDiagonalUpperRightToLowerLeft ()
                }

                // Lower Right?
                else if (LAST_ROW_COL == row && LAST_ROW_COL == col) {
                    hand = getHandDiagonalUpperLeftToLowerRight ()
                }

                // Horiz top?
                else if (ZERO == row) {
                    hand = getHandByColumn (col)
                }

                // Horiz Bottom
                else if (LAST_ROW_COL == row) {
                    hand = getHandByColumn (col)
                }

                // Vert Left Side
                else if (ZERO == col) {
                    hand = getHandByRow (row)
                }

                // Vert Right Side
                else if (LAST_ROW_COL == col) {
                    hand = getHandByRow (row)
                }
            }
        }

        return hand
    }

    /**
     * Is there any value there?
     */
    fun hasValue (hand: Array<Die>): Boolean {
        var result = false
        for (item in hand) {
            if (item.rank> NO_RANK) {
                result = true
                break
            }
        }
        return result
    }

    /**
     * Get the board size.
     */
    fun size (): Int {
        return board.size
    }

    /**
     * Get the dice at this location.
     */
    fun getDie (index: Int): Die {
        if (index>= ZERO && index < NUM_SQUARES) {
            return board[index]
        }
        throw ArrayIndexOutOfBoundsException ("Index %index out of bounds.")
    }

    /**
     * Get the dice at this location.
     */
    fun setDie (index: Int, die: Die) {
        board[index] = die
    }
}