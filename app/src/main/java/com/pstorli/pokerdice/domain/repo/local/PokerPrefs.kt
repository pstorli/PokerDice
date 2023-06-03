package com.pstorli.pokerdice.repo.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.repo.PokerDS
import com.pstorli.pokerdice.util.Consts.BET_NAME
import com.pstorli.pokerdice.util.Consts.BLUE_NAME
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE_VAL
import com.pstorli.pokerdice.util.Consts.CASH_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE1_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE2_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE3_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE4_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE5_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE6_NAME
import com.pstorli.pokerdice.util.Consts.EMPTY_SQUARE_VAL
import com.pstorli.pokerdice.util.Consts.GREEN_NAME
import com.pstorli.pokerdice.util.Consts.RED_NAME
import com.pstorli.pokerdice.util.Consts.ROLLS_NAME
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
                Persist.BET                 -> pokerDAO.bet             = getInt        (BET_NAME)
                Persist.BOARD               -> pokerDAO.board           = loadBoard     ()
                Persist.CASH                -> pokerDAO.cash            = getInt        (CASH_NAME)
                Persist.COLOR_DICE1         -> pokerDAO.colorDice1      = getColorPair  (COLOR_DICE1_NAME)
                Persist.COLOR_DICE2         -> pokerDAO.colorDice2      = getColorPair  (COLOR_DICE2_NAME)
                Persist.COLOR_DICE3         -> pokerDAO.colorDice3      = getColorPair  (COLOR_DICE3_NAME)
                Persist.COLOR_DICE4         -> pokerDAO.colorDice4      = getColorPair  (COLOR_DICE4_NAME)
                Persist.COLOR_DICE5         -> pokerDAO.colorDice5      = getColorPair  (COLOR_DICE5_NAME)
                Persist.COLOR_DICE6         -> pokerDAO.colorDice6      = getColorPair  (COLOR_DICE6_NAME)
                Persist.ROLLS               -> pokerDAO.rolls           = getInt        (ROLLS_NAME)
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
                Persist.BET                 -> putInt       (BET_NAME,              pokerDAO.bet)
                Persist.BOARD               -> saveBoard    (                       pokerDAO.board)
                Persist.CASH                -> putInt       (CASH_NAME,             pokerDAO.cash)
                Persist.COLOR_DICE1         -> putColorPair (COLOR_DICE1_NAME,      pokerDAO.colorDice1)
                Persist.COLOR_DICE2         -> putColorPair (COLOR_DICE2_NAME,      pokerDAO.colorDice2)
                Persist.COLOR_DICE3         -> putColorPair (COLOR_DICE3_NAME,      pokerDAO.colorDice3)
                Persist.COLOR_DICE4         -> putColorPair (COLOR_DICE4_NAME,      pokerDAO.colorDice4)
                Persist.COLOR_DICE5         -> putColorPair (COLOR_DICE5_NAME,      pokerDAO.colorDice5)
                Persist.COLOR_DICE6         -> putColorPair (COLOR_DICE6_NAME,      pokerDAO.colorDice6)
                Persist.ROLLS               -> putInt       (ROLLS_NAME,            pokerDAO.rolls)
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
        for (row in 0 until BOARD_SIZE_VAL) {
            for (col in 0 until BOARD_SIZE_VAL) {
                putInt (squareName(row,col), EMPTY_SQUARE_VAL)
            }
        }
    }

    /**
     * Fetch the current board.
     */
    private fun loadBoard (): Array<IntArray> {
        // Create an empty board, then fill it with values.
        val board = Array(BOARD_SIZE_VAL) { IntArray(BOARD_SIZE_VAL) }

        // Loop through the rows and cols.
        for (row in 0 until BOARD_SIZE_VAL) {
            for (col in 0 until BOARD_SIZE_VAL) {
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
        for (row in 0 until BOARD_SIZE_VAL) {
            for (col in 0 until BOARD_SIZE_VAL) {
                putInt (squareName(row,col), board[row][col])
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Get/Set Color prefs.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a color pair value.
     */
    private fun getColorPair (name: Pair <String, String>): Pair <Color,Color> {
        // Return the RGB
        return Pair (getColor (name.first), getColor (name.second))
    }

    /**
     * Save a color value.
     */
    private fun putColorPair (name: Pair <String, String>, value: Pair <Color,Color>) {
        putColor(name.first,  value.first)
        putColor(name.second, value.second)
    }

    /**
     * Fetch a color value.
     */
    private fun getColor (name: String): Color {
        // Return the RGB
        return Color (
            getFloat (name+RED_NAME),
            getFloat (name+GREEN_NAME),
            getFloat (name+BLUE_NAME))
    }

    /**
     * Save a color value.
     */
    private fun putColor (name: String, value: Color) {
        putFloat (name+RED_NAME,     value.red)
        putFloat (name+GREEN_NAME,   value.green)
        putFloat (name+BLUE_NAME,    value.blue)
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