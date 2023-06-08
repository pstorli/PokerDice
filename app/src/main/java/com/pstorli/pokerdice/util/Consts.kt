package com.pstorli.pokerdice.util

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.domain.model.Dice
import com.pstorli.pokerdice.ui.theme.COLOR_DK_BACK
import com.pstorli.pokerdice.ui.theme.COLOR_DK_BET_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_DK_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_DK_CASH
import com.pstorli.pokerdice.ui.theme.COLOR_DK_CASH_OUT
import com.pstorli.pokerdice.ui.theme.COLOR_DK_EDGE
import com.pstorli.pokerdice.ui.theme.COLOR_DK_HOLD_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_DK_ROLL_DICE
import com.pstorli.pokerdice.ui.theme.COLOR_DK_TEXT
import com.pstorli.pokerdice.ui.theme.COLOR_LT_BACK
import com.pstorli.pokerdice.ui.theme.COLOR_LT_BET_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_LT_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_LT_CASH
import com.pstorli.pokerdice.ui.theme.COLOR_LT_CASH_OUT
import com.pstorli.pokerdice.ui.theme.COLOR_LT_EDGE
import com.pstorli.pokerdice.ui.theme.COLOR_LT_HOLD_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_LT_ROLLS
import com.pstorli.pokerdice.ui.theme.COLOR_LT_ROLL_DICE
import com.pstorli.pokerdice.ui.theme.COLOR_LT_TEXT

/**
 * The main reason to use constants, is
 * that we can keep everyone in agreement -
 * even it is wrong, we are all wrong and if
 * right we are all-right.
 *
 * In either case both still work.
 *
 * ALSO - The values below are for lookup only,
 * do not use to display to the user - use localization instead
 * I am using them for logging.
 * See Strings.xml
 *
 */
object Consts {
    val BET_NAME                        = "Bet"
    val BORDER_DEFAULT_WIDTH_VAL        = 2.dp
    val BLUE_NAME                       = "Blue"
    val BOARD_SIZE                      = 7
    val CASH_NAME                       = "Cash"

    val DEFAULT_ELEVATION_VAL           = 10.dp

    val DICE_ZERO                       = 0
    val DICE_ONE                        = 1
    val DICE_TWO                        = 2
    val DICE_THREE                      = 3
    val DICE_FOUR                       = 4
    val DICE_FIVE                       = 5
    val DICE_SIX                        = 6

    val PRESSED_ELEVATION_VAL           = 15.dp
    val DISABLED_ELEVATION_VAL          = 0.dp
    val GAME_SAVED                      = "Game saved"
    val GREEN_NAME                      = "Green"
    val HUNDRED_VAL                     = 100
    val PADDING_DEFAULT_VAL             = 16.dp
    val RED_NAME                        = "Red"
    val ROLLS_NAME                      = "Rolls"
    val ROUNDED_CORNER_SHAPE_PCT_VAL    = 20
    val TAG_NAME                        = "PokerDice"
    val ZERO_VAL                        = 0

    // *********************************************************************************************
    // Colors
    // *********************************************************************************************

    // Color Names
    val COLOR_DK_BACK_NAME          = "Color Dark Back"
    val COLOR_DK_BET_BORDER_NAME    = "Color Dark Bet Border"
    val COLOR_DK_BORDER_NAME        = "Color Dark Border"
    val COLOR_DK_CASH_NAME          = "Color Dark Cash"
    val COLOR_DK_CASH_OUT_NAME      = "Color Dark Cash Out"

    val COLOR_DK_DICE0_NAME         = "Dice0 Dark Color"
    val COLOR_DK_DICE1_NAME         = "Dice1 Dark Color"
    val COLOR_DK_DICE2_NAME         = "Dice2 Dark Color"
    val COLOR_DK_DICE3_NAME         = "Dice3 Dark Color"
    val COLOR_DK_DICE4_NAME         = "Dice4 Dark Color"
    val COLOR_DK_DICE5_NAME         = "Dice5 Dark Color"
    val COLOR_DK_DICE6_NAME         = "Dice6 Dark Color"

    val COLOR_DK_EDGE_NAME          = "Color Dark Edge"
    val COLOR_DK_HOLD_BORDER_NAME   = "Color Dark Hold Border"
    val COLOR_DK_ROLL_DICE_NAME     = "Color Dark Roll Dice"
    val COLOR_DK_ROLLS_NAME         = "Color Dark Rolls"
    val COLOR_DK_TEXT_NAME          = "Color Dark Text"

    val COLOR_LT_BACK_NAME          = "Color Light Back"
    val COLOR_LT_BET_BORDER_NAME    = "Color Light Bet Border"
    val COLOR_LT_BORDER_NAME        = "Color Light Border"
    val COLOR_LT_CASH_NAME          = "Color Light Cash"
    val COLOR_LT_CASH_OUT_NAME      = "Color Light Cash Out"

    val COLOR_LT_DICE0_NAME         = "Dice0 Light Color"
    val COLOR_LT_DICE1_NAME         = "Dice1 Light Color"
    val COLOR_LT_DICE2_NAME         = "Dice2 Light Color"
    val COLOR_LT_DICE3_NAME         = "Dice3 Light Color"
    val COLOR_LT_DICE4_NAME         = "Dice4 Light Color"
    val COLOR_LT_DICE5_NAME         = "Dice5 Light Color"
    val COLOR_LT_DICE6_NAME         = "Dice6 Light Color"

    val COLOR_LT_EDGE_NAME          = "Color Light Edge"
    val COLOR_LT_HOLD_BORDER_NAME   = "Color Light Hold Border"
    val COLOR_LT_ROLL_DICE_NAME     = "Color Light Roll Dice"
    val COLOR_LT_ROLLS_NAME         = "Color Light Rolls"
    val COLOR_LT_TEXT_NAME          = "Color Light Text"

    // Color pairs.
    val COLOR_DICE0_NAME            = Pair (COLOR_LT_DICE0_NAME, COLOR_DK_DICE0_NAME)
    val COLOR_DICE1_NAME            = Pair (COLOR_LT_DICE1_NAME, COLOR_DK_DICE1_NAME)
    val COLOR_DICE2_NAME            = Pair (COLOR_LT_DICE2_NAME, COLOR_DK_DICE2_NAME)
    val COLOR_DICE3_NAME            = Pair (COLOR_LT_DICE3_NAME, COLOR_DK_DICE3_NAME)
    val COLOR_DICE4_NAME            = Pair (COLOR_LT_DICE4_NAME, COLOR_DK_DICE4_NAME)
    val COLOR_DICE5_NAME            = Pair (COLOR_LT_DICE5_NAME, COLOR_DK_DICE5_NAME)
    val COLOR_DICE6_NAME            = Pair (COLOR_LT_DICE6_NAME, COLOR_DK_DICE6_NAME)

    val COLOR_BACK_NAME             = Pair (COLOR_LT_BACK_NAME, COLOR_DK_BACK_NAME)
    var COLOR_BET_BORDER_NAME       = Pair (COLOR_LT_BET_BORDER_NAME, COLOR_DK_BET_BORDER_NAME)
    var COLOR_BORDER_NAME           = Pair (COLOR_LT_BORDER_NAME, COLOR_DK_BORDER_NAME)
    var COLOR_CASH_NAME             = Pair (COLOR_LT_CASH_NAME, COLOR_DK_CASH_NAME)
    var COLOR_CASH_OUT_NAME         = Pair (COLOR_LT_CASH_OUT_NAME, COLOR_DK_CASH_OUT_NAME)
    var COLOR_EDGE_NAME             = Pair (COLOR_LT_EDGE_NAME, COLOR_DK_EDGE_NAME)
    var COLOR_HOLD_BORDER_NAME      = Pair (COLOR_LT_HOLD_BORDER_NAME, COLOR_DK_HOLD_BORDER_NAME)
    var COLOR_ROLL_DICE_NAME        = Pair (COLOR_LT_ROLL_DICE_NAME, COLOR_DK_ROLL_DICE_NAME)
    var COLOR_ROLLS_NAME            = Pair (COLOR_LT_ROLLS_NAME, COLOR_DK_ROLLS_NAME)
    var COLOR_TEXT_NAME             = Pair (COLOR_LT_TEXT_NAME, COLOR_DK_TEXT_NAME)

    // *********************************************************************************************
    // More helper functions
    // *********************************************************************************************

    /**
     * Is this a edge sqaure?
     */
    fun isEdgeSquare (index: Int): Boolean {
        val rowCol = rowCol (index)
        return if (0 == rowCol.first || BOARD_SIZE-1 == rowCol.first || 0 == rowCol.second || BOARD_SIZE-1 == rowCol.second)
            true else false
    }

    /**
     * Convert an int to a Dice enum.
     */
    fun getDice (num: Int): Dice {
        var result = Dice.Zero
        when (num) {
            DICE_ZERO   -> result = Dice.Zero
            DICE_ONE    -> result = Dice.One
            DICE_TWO    -> result = Dice.Two
            DICE_THREE  -> result = Dice.Three
            DICE_FOUR   -> result = Dice.Four
            DICE_FIVE   -> result = Dice.Five
            DICE_SIX    -> result = Dice.Six
        }
        return result
    }

    /**
     * Convert an int to a Dice enum.
     */
    fun getDiceNum (dice: Dice): Int {
        var result = DICE_ZERO
        when (dice) {
            Dice.Zero   -> result = DICE_ZERO
            Dice.One    -> result = DICE_ONE
            Dice.Two    -> result = DICE_TWO
            Dice.Three  -> result = DICE_THREE
            Dice.Four   -> result = DICE_FOUR
            Dice.Five   -> result = DICE_FIVE
            Dice.Six    -> result = DICE_SIX
        }
        return result
    }

    /**
     * Deterine the row,col from the index.
     */
    fun rowCol (index: Int): Pair<Int,Int> {
        val row  = index / BOARD_SIZE
        val col  = index % BOARD_SIZE
        return Pair (row,col)
    }

    /**
     * Get the index from a row,col
     */
    fun index (row:Int, col: Int): Int {
        return row * BOARD_SIZE + col
    }

    /**
     * Given a row/col create a unique square name.
     */
    fun squareName (index: Int): String {
        return "Square $index"
    }

    /**
     * Set night/dark mode.
     */
    fun setDarkMode (context: Context, state: Boolean) {
        val uiManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        if (state) {
            uiManager.nightMode = UiModeManager.MODE_NIGHT_YES
        } else {
            uiManager.nightMode = UiModeManager.MODE_NIGHT_NO
        }
    }

    /**
     * Toggle night/dark mode?
     */
    fun toggleDarkMode (context: Context) {
        setDarkMode (context,inDarkMode (context))
    }

    /**
     * Are we in night/dark mode?
     */
    fun inDarkMode (context: Context): Boolean {
        val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * Fetch a color value based on light or dark mode.
     */
    fun color (name: Pair <String, String>, context: Context): Color {
        // Return the RGB
        if (inDarkMode (context)) {
            if (name.second.equals(COLOR_BACK_NAME.second)) {
                return COLOR_DK_BACK
            }
            else if (name.second.equals(COLOR_BET_BORDER_NAME.second)) {
                return COLOR_DK_BET_BORDER
            }
            else if (name.second.equals(COLOR_BORDER_NAME.second)) {
                return COLOR_DK_BORDER
            }
            else if (name.second.equals(COLOR_CASH_NAME.second)) {
                return COLOR_DK_CASH
            }
            else if (name.second.equals(COLOR_CASH_OUT_NAME.second)) {
                return COLOR_DK_CASH_OUT
            }
            else if (name.second.equals(COLOR_EDGE_NAME.second)) {
                return COLOR_DK_EDGE
            }
            else if (name.second.equals(COLOR_HOLD_BORDER_NAME.second)) {
                return COLOR_DK_HOLD_BORDER
            }
            else if (name.second.equals(COLOR_ROLL_DICE_NAME.second)) {
                return COLOR_DK_ROLL_DICE
            }
            else if (name.second.equals(COLOR_TEXT_NAME.second)) {
                return COLOR_DK_TEXT
            }
            else {
                // Return something
                return COLOR_DK_BACK
            }
        }
        else {
            // Light mode.
            if (name.first.equals(COLOR_BACK_NAME.first)) {
                return COLOR_LT_BACK
            }
            else if (name.first.equals(COLOR_BET_BORDER_NAME.first)) {
                return COLOR_LT_BET_BORDER
            }
            else if (name.first.equals(COLOR_BORDER_NAME.first)) {
                return COLOR_LT_BORDER
            }
            else if (name.first.equals(COLOR_CASH_NAME.first)) {
                return COLOR_LT_CASH
            }
            else if (name.first.equals(COLOR_CASH_OUT_NAME.first)) {
                return COLOR_LT_CASH_OUT
            }
            else if (name.second.equals(COLOR_EDGE_NAME.second)) {
                return COLOR_LT_EDGE
            }
            else if (name.first.equals(COLOR_HOLD_BORDER_NAME.first)) {
                return COLOR_LT_HOLD_BORDER
            }
            else if (name.first.equals(COLOR_ROLL_DICE_NAME.first)) {
                return COLOR_LT_ROLL_DICE
            }
            else if (name.first.equals(COLOR_ROLLS_NAME.first)) {
                return COLOR_LT_ROLLS
            }
            else if (name.first.equals(COLOR_TEXT_NAME.first)) {
                return COLOR_LT_TEXT
            }
            else {
                // Return something
                return COLOR_LT_BACK
            }
        }
    }

    // *********************************************************************************************
    // Log helper functions
    // *********************************************************************************************

    /**
     * Log an error message.
     */
    @Suppress("unused")
    fun logError(ex: Exception) {
        logError(TAG_NAME, ex.toString())
    }

    /**
     * Log an error message.
     */
    fun logError(msg: String) {
        logError(TAG_NAME, msg)
    }

    /**
     * Log an error message.
     */
    fun logError(msg: String, t: Throwable? = null) {
        Log.e(TAG_NAME, msg, t)
    }

    /**
     * Log an error message.
     */
    fun logError(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    /**
     * Log an error message.
     */
    fun logWarning(msg: String) {
        Log.w(TAG_NAME, msg)
    }

    /**
     * Log an error message.
     */
    fun logWarning(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    /**
     * Log an info message.
     */
    fun logInfo(msg: String) {
        Log.i(TAG_NAME, msg)
    }

    /**
     * Log an info message.
     */
    fun logInfo(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    /**
     * Log a debug message.
     */
    fun debug (msg: String) {
        Log.i(TAG_NAME, msg)
    }
}