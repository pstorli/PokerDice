package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.SQUARE_LAST
import com.pstorli.pokerdice.util.Consts.DICE_IN_HAND
import com.pstorli.pokerdice.util.Consts.FIRST_ROW_COL
import com.pstorli.pokerdice.util.Consts.LAST_ROW_COL
import com.pstorli.pokerdice.util.Consts.NO_RANK
import com.pstorli.pokerdice.util.Consts.NUM_SQUARES
import com.pstorli.pokerdice.util.Consts.SQUARE_BET_COST
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
        return isEdgeSquareTop (rowCol.first, rowCol.second) || isEdgeSquareBottom (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareTop (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareTop (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareTop (row: Int, col: Int): Boolean {
        return if (SQUARE_FIRST == row)
            true else false
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareBottom (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareBottom (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareBottom (row: Int, col: Int): Boolean {
        return if (SQUARE_LAST == row)
            true else false
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareSide (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareLeft (rowCol.first, rowCol.second) || isEdgeSquareRight (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareLeft (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareLeft (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareLeft (row: Int, col: Int): Boolean {
        return if (SQUARE_FIRST == col)
            true else false
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareRight (index: Int): Boolean {
        val rowCol = rowCol(index)
        return isEdgeSquareRight (rowCol.first, rowCol.second)
    }

    /**
     * Is this a edge square?
     */
    fun isEdgeSquareRight (row: Int, col: Int): Boolean {
        return if (SQUARE_LAST == col)
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
     * Is this the upper left corner square?
     */
    fun isCornerUL (index: Int): Boolean {
        val rowCol = rowCol (index)
        return SQUARE_FIRST == rowCol.first && SQUARE_FIRST == rowCol.second
    }

    /**
     * Is this the upper right corner square?
     */
    fun isCornerUR (index: Int): Boolean {
        val rowCol = rowCol (index)
        return SQUARE_FIRST == rowCol.first && SQUARE_LAST == rowCol.second
    }

    /**
     * Is this the lower left corner square?
     */
    fun isCornerLL (index: Int): Boolean {
        val rowCol = rowCol (index)
        return SQUARE_LAST == rowCol.first && SQUARE_FIRST == rowCol.second
    }

    /**
     * Is this the lower right corner square?
     */
    fun isCornerLR (index: Int): Boolean {
        val rowCol = rowCol (index)
        return SQUARE_LAST == rowCol.first && SQUARE_LAST == rowCol.second
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

        else if (isEdgeSquareLeft (row, col) || isEdgeSquareRight (row, col)) {
            oppCol = SQUARE_LAST - col
        }

        else if (isEdgeSquareTop(row, col) || isEdgeSquareBottom(row, col)) {
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
                bets+= SQUARE_BET_COST
            }
            if (board [index(SQUARE_LAST, which)].selected) {
                bets+= SQUARE_BET_COST
            }
        }

        // Left / Right
        for (which in SQUARE_FIRST +1 .. SQUARE_LAST -1) {
            if (board [index(which, SQUARE_FIRST)].selected) {
                bets+= SQUARE_BET_COST
            }
            if (board [index(which, SQUARE_LAST)].selected) {
                bets+= SQUARE_BET_COST
            }
        }

        // Diagonal.

        // UL
        if (board [index(SQUARE_FIRST, SQUARE_FIRST)].selected) {
            bets+= SQUARE_BET_COST
        }

        // UR
        else if (board [index(SQUARE_FIRST, SQUARE_LAST)].selected) {
            bets+= SQUARE_BET_COST
        }

        // LL
        else if (board [index(SQUARE_LAST, SQUARE_FIRST)].selected) {
            bets+= SQUARE_BET_COST
        }

        // LR
        else if (board [index(SQUARE_LAST, SQUARE_LAST)].selected) {
            bets+= SQUARE_BET_COST
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
            if (index>= SQUARE_FIRST && index< NUM_SQUARES) {
                // This index is on the board.
                val row = row(index)
                val col = col(index)

                // *****************************************************************************
                // Rank these
                // *****************************************************************************

                // Upper Left?
                if (SQUARE_FIRST == row && SQUARE_FIRST == col) {
                    hand = getHandDiagonalUpperLeftToLowerRight ()
                }

                // Lower left?
                else if (SQUARE_LAST == row && SQUARE_FIRST == col) {
                    hand = getHandDiagonalUpperRightToLowerLeft ()
                }

                // Upper Right?
                else if (SQUARE_FIRST == row && SQUARE_LAST == col) {
                    hand = getHandDiagonalUpperRightToLowerLeft ()
                }

                // Lower Right?
                else if (SQUARE_LAST == row && SQUARE_LAST == col) {
                    hand = getHandDiagonalUpperLeftToLowerRight ()
                }

                // Horiz top?
                else if (SQUARE_FIRST == row) {
                    hand = getHandByColumn (col)
                }

                // Horiz Bottom
                else if (SQUARE_LAST == row) {
                    hand = getHandByColumn (col)
                }

                // Vert Left Side
                else if (SQUARE_FIRST == col) {
                    hand = getHandByRow (row)
                }

                // Vert Right Side
                else if (SQUARE_LAST == col) {
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