package com.pstorli.pokerdice.repo.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.repo.PokerDS
import com.pstorli.pokerdice.util.Consts.BET
import com.pstorli.pokerdice.util.Consts.BLUE
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.CASH
import com.pstorli.pokerdice.util.Consts.COLOR_BET_BORDER
import com.pstorli.pokerdice.util.Consts.COLOR_BORDER
import com.pstorli.pokerdice.util.Consts.COLOR_DICE1
import com.pstorli.pokerdice.util.Consts.COLOR_DICE2
import com.pstorli.pokerdice.util.Consts.COLOR_DICE3
import com.pstorli.pokerdice.util.Consts.COLOR_DICE4
import com.pstorli.pokerdice.util.Consts.COLOR_DICE5
import com.pstorli.pokerdice.util.Consts.COLOR_DICE6
import com.pstorli.pokerdice.util.Consts.COLOR_HOLD_BORDER
import com.pstorli.pokerdice.util.Consts.COLOR_TEXT
import com.pstorli.pokerdice.util.Consts.EMPTY_SQUARE
import com.pstorli.pokerdice.util.Consts.GREEN
import com.pstorli.pokerdice.util.Consts.RED
import com.pstorli.pokerdice.util.Consts.ROLLS
import com.pstorli.pokerdice.util.Consts.squareName
import com.pstorli.pokerdice.util.Persist

/**
 * This class saves and loads values from preferences.
 * Actual save / load functions that use editor and prefs
 * at bottom as primitives.
 *
 * Other functions, use primitive functions at bottom to load/save larger classes.
 */
class PokerPrefs (application: Application) : PokerDS {

    // *********************************************************************************************
    // PokerDS Vars
    // *********************************************************************************************
    private val PREFERENCE_FILE_NAME = "Poker.prefs"

    private val pref: SharedPreferences = application.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    private val editor = pref.edit()

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
                Persist.BET                 -> pokerDAO.bet             = getInt     (BET)
                Persist.BOARD               -> pokerDAO.board           = loadBoard  ()
                Persist.CASH                -> pokerDAO.cash            = getInt     (CASH)
                Persist.COLOR_BET_BORDER    -> pokerDAO.colorBetBorder  = getColor   (COLOR_BET_BORDER)
                Persist.COLOR_BORDER        -> pokerDAO.colorBorder     = getColor   (COLOR_BORDER)
                Persist.COLOR_DICE1         -> pokerDAO.colorDice1      = getColor   (COLOR_DICE1)
                Persist.COLOR_DICE2         -> pokerDAO.colorDice2      = getColor   (COLOR_DICE2)
                Persist.COLOR_DICE3         -> pokerDAO.colorDice3      = getColor   (COLOR_DICE3)
                Persist.COLOR_DICE4         -> pokerDAO.colorDice4      = getColor   (COLOR_DICE4)
                Persist.COLOR_DICE5         -> pokerDAO.colorDice5      = getColor   (COLOR_DICE5)
                Persist.COLOR_DICE6         -> pokerDAO.colorDice6      = getColor   (COLOR_DICE6)
                Persist.COLOR_HOLD_BORDER   -> pokerDAO.colorHoldBorder = getColor   (COLOR_HOLD_BORDER)
                Persist.COLOR_TEXT          -> pokerDAO.colorText       = getColor   (COLOR_TEXT)
                Persist.ROLLS               -> pokerDAO.rolls           = getInt     (ROLLS)
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
                Persist.BET                 -> putInt       (BET,               pokerDAO.bet)
                Persist.BOARD               -> saveBoard    (                   pokerDAO.board)
                Persist.CASH                -> putInt       (CASH,              pokerDAO.cash)
                Persist.COLOR_BET_BORDER    -> putColor     (COLOR_BET_BORDER,  pokerDAO.colorBetBorder)
                Persist.COLOR_BORDER        -> putColor     (COLOR_BORDER,  pokerDAO.colorBorder)
                Persist.COLOR_DICE1         -> putColor     (COLOR_DICE1,       pokerDAO.colorDice1)
                Persist.COLOR_DICE2         -> putColor     (COLOR_DICE2,       pokerDAO.colorDice2)
                Persist.COLOR_DICE3         -> putColor     (COLOR_DICE3,       pokerDAO.colorDice3)
                Persist.COLOR_DICE4         -> putColor     (COLOR_DICE4,       pokerDAO.colorDice4)
                Persist.COLOR_DICE5         -> putColor     (COLOR_DICE5,       pokerDAO.colorDice5)
                Persist.COLOR_DICE6         -> putColor     (COLOR_DICE6,       pokerDAO.colorDice6)
                Persist.COLOR_HOLD_BORDER   -> putColor     (COLOR_HOLD_BORDER, pokerDAO.colorHoldBorder)
                Persist.COLOR_TEXT          -> putColor     (COLOR_TEXT,        pokerDAO.colorText)
                Persist.ROLLS               -> putInt       (ROLLS,             pokerDAO.rolls)
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
    private fun resetBoard () {
        // Loop through the rows and cols.
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                putInt (squareName(row,col), EMPTY_SQUARE)
            }
        }
    }

    /**
     * Fetch the current board.
     */
    private fun loadBoard (): Array<IntArray> {
        // Create an empty board, then fill it with values.
        val board = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }

        // Loop through the rows and cols.
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                // Get saved pref board row/col value and assign to board.
                board[row][col] = getInt (squareName(row,col))
            }
        }

        return board
    }

    /**
     * Save the board, overrwrite current board.
     */
    private fun saveBoard (board: Array<IntArray>) {
        // Loop through the rows and cols.
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                putInt (squareName(row,col), board[row][col])
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Get/Set Color prefs.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a color value.
     */
    private fun getColor (name: String): Color {
        // Return the RGB
        return Color (
            getFloat (name+RED),
            getFloat (name+GREEN),
            getFloat (name+BLUE))
    }

    /**
     * Save a color value.
     */
    private fun putColor (name: String, value: Color) {
        putFloat (name+RED,     value.red)
        putFloat (name+GREEN,   value.green)
        putFloat (name+BLUE,    value.blue)
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Get/Set Primitives (Int, String, Float, etc.) prefs. Uses pref and editor
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a string value.
     */
    @Suppress("unused")
    private fun get (name: String): String {
        return pref.getString(name, "")!!
    }

    /**
     * Save a string.
     */
    @Suppress("unused")
    private fun put (name: String, value: String) {
        editor.putString(name, value)
        editor.commit()
    }

    /**
     * Fetch an int value.
     */
    private fun getInt (name: String): Int {
        return pref.getInt(name, 0)
    }

    /**
     * Save a string.
     */
    private fun putInt (name: String, value: Int) {
        editor.putInt(name, value)
        editor.commit()
    }

    /**
     * Fetch an int value.
     */
    private fun getFloat (name: String): Float {
        return pref.getFloat(name, 0.0F)
    }

    /**
     * Save a string.
     */
    private fun putFloat (name: String, value: Float) {
        editor.putFloat(name, value)
        editor.commit()
    }
}