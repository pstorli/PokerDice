package com.pstorli.pokerdice.repo.local

import com.pstorli.pokerdice.domain.model.Dice
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.repo.PokerDS
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.BET_NAME
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.CASH_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE0_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE1_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE2_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE3_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE4_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE5_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE6_NAME
import com.pstorli.pokerdice.util.Consts.DICE_ZERO
import com.pstorli.pokerdice.util.Consts.ROLLS_NAME
import com.pstorli.pokerdice.util.Consts.getDice
import com.pstorli.pokerdice.util.Consts.getDiceNum
import com.pstorli.pokerdice.util.Consts.squareName
import com.pstorli.pokerdice.util.Persist
import com.pstorli.pokerdice.util.Prefs

/**
 * This class saves and loads values from preferences.
 * Actual save / load functions that use editor and prefs
 * at bottom as primitives.
 *
 * Other functions, use primitive functions at bottom to load/save larger classes.
 */
class PokerPrefs (var prefs: Prefs) : PokerDS {

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The whole enchilada!
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load the dao object that has all our state.
     *
     * @param what - Array of Persist items to load, or Persist.ALL
     */
    override suspend fun loadDAO (what: Array<Persist>): PokerDAO
    {
        // Loop through items to load.
        val pokerDAO = PokerDAO ()
        for (which in what) {
            when (which) {
                Persist.BET                 -> pokerDAO.bet             = prefs.getInt        (BET_NAME)
                Persist.BOARD               -> pokerDAO.board           = loadBoard     ()
                Persist.CASH                -> pokerDAO.cash            = prefs.getInt        (CASH_NAME)
                Persist.COLOR_DICE0         -> pokerDAO.colorDice0      = prefs.getColorPair  (COLOR_DICE0_NAME)
                Persist.COLOR_DICE1         -> pokerDAO.colorDice1      = prefs.getColorPair  (COLOR_DICE1_NAME)
                Persist.COLOR_DICE2         -> pokerDAO.colorDice2      = prefs.getColorPair  (COLOR_DICE2_NAME)
                Persist.COLOR_DICE3         -> pokerDAO.colorDice3      = prefs.getColorPair  (COLOR_DICE3_NAME)
                Persist.COLOR_DICE4         -> pokerDAO.colorDice4      = prefs.getColorPair  (COLOR_DICE4_NAME)
                Persist.COLOR_DICE5         -> pokerDAO.colorDice5      = prefs.getColorPair  (COLOR_DICE5_NAME)
                Persist.COLOR_DICE6         -> pokerDAO.colorDice6      = prefs.getColorPair  (COLOR_DICE6_NAME)
                Persist.ROLLS               -> pokerDAO.rolls           = prefs.getInt        (ROLLS_NAME)
            }
        }
        return pokerDAO
    }

    /**
     * Save the dao object that has all our state.
     *
     * @param what - Array of Persist items to save, or Persist.ALL
     */
    override suspend fun saveDAO (pokerDAO: PokerDAO, what: Array<Persist>)
    {
        // Loop through items to save.
        for (which in what) {
            when (which) {
                Persist.BET                 -> prefs.putInt       (BET_NAME,              pokerDAO.bet)
                Persist.BOARD               -> saveBoard          (                       pokerDAO.board)
                Persist.CASH                -> prefs.putInt       (CASH_NAME,             pokerDAO.cash)
                Persist.COLOR_DICE0         -> prefs.putColorPair (COLOR_DICE0_NAME,      pokerDAO.colorDice0)
                Persist.COLOR_DICE1         -> prefs.putColorPair (COLOR_DICE1_NAME,      pokerDAO.colorDice1)
                Persist.COLOR_DICE2         -> prefs.putColorPair (COLOR_DICE2_NAME,      pokerDAO.colorDice2)
                Persist.COLOR_DICE3         -> prefs.putColorPair (COLOR_DICE3_NAME,      pokerDAO.colorDice3)
                Persist.COLOR_DICE4         -> prefs.putColorPair (COLOR_DICE4_NAME,      pokerDAO.colorDice4)
                Persist.COLOR_DICE5         -> prefs.putColorPair (COLOR_DICE5_NAME,      pokerDAO.colorDice5)
                Persist.COLOR_DICE6         -> prefs.putColorPair (COLOR_DICE6_NAME,      pokerDAO.colorDice6)
                Persist.ROLLS               -> prefs.putInt       (ROLLS_NAME,            pokerDAO.rolls)
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Reset, get or set the board.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Reset the board.
     */
    @Suppress("unused")
    fun resetBoard () {
        // Loop through the rows and cols.
        for (index in 0 until BOARD_SIZE*BOARD_SIZE) {
            prefs.putInt (squareName(index), DICE_ZERO)
        }
    }

    /**
     * Fetch the current board.
     */
    fun loadBoard (): Array<Dice> {
        // Create an empty board, then fill it with values.
        val board = Array(BOARD_SIZE*BOARD_SIZE) { Dice.Zero }

        // Loop through the rows and cols.
        for (index in 0 until BOARD_SIZE*BOARD_SIZE) {
            // Get saved pref board row/col value and assign to board.
            board[index] = getDice (prefs.getInt (squareName(index)))
        }

        return board
    }

    /**
     * Save the board, overrwrite current board.
     */
    fun saveBoard (board: Array<Dice>) {
        prefs.putBool(Consts.GAME_SAVED, true)

        // Loop through the rows and cols.
        for (index in 0 until BOARD_SIZE*BOARD_SIZE) {
            prefs.putInt (squareName(index), getDiceNum (board.get(index)))
        }
    }
}