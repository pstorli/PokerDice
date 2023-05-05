package com.pstorli.pokerdice.util

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build.VERSION
import android.util.Log
import androidx.compose.ui.unit.dp


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
    val BET                         = "Bet"
    val BLUE                        = "Blue"
    val BOARD_SIZE                  = 7
    val CASH                        = "Cash"
    val COLOR_BACK                  = "Background Color"
    val COLOR_BET_BORDER            = "Bet Border Color" // The selected (bet line) border color.
    val COLOR_BORDER                = "Border Color"     // The default border color.
    val COLOR_DICE1                 = "Dice1 Color"
    val COLOR_DICE2                 = "Dice2 Color"
    val COLOR_DICE3                 = "Dice3 Color"
    val COLOR_DICE4                 = "Dice4 Color"
    val COLOR_DICE5                 = "Dice5 Color"
    val COLOR_DICE6                 = "Dice6 Color"
    val COLOR_HOLD_BORDER           = "Hold Border Color" // The hold border color.
    val COLOR_TEXT                  = "Text Color"      // The default text color.
    val DEFAULT_ELEVATION           = 10.dp
    val PRESSED_ELEVATION           = 15.dp
    val DISABLED_ELEVATION          = 0.dp
    val EMPTY_SQUARE                = 0
    val GREEN                       = "Green"
    val HUNDRED                     = 100
    val PADDING_DEFAULT             = 10.dp
    val RED                         = "Red"
    val ROLLS                       = "Rolls"
    val ROUNDED_CORNER_SHAPE_PCT    = 20
    val TAG                         = "PokerDice"
    val ZERO                        = 0

    // *********************************************************************************************
    // Log helper functions
    // *********************************************************************************************

    /**
     * Log an error message.
     */
    @Suppress("unused")
    fun logError(ex: Exception) {
        logError(TAG, ex.toString())
    }

    /**
     * Log an error message.
     */
    fun logError(msg: String) {
        logError(TAG, msg)
    }

    /**
     * Log an error message.
     */
    fun logError(msg: String, t: Throwable? = null) {
        Log.e(TAG, msg, t)
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
        Log.w(TAG, msg)
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
        Log.i(TAG, msg)
    }

    /**
     * Log an info message.
     */
    fun logInfo(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    // *********************************************************************************************
    // More helper functions
    // *********************************************************************************************

    /**
     * Given a row/col create a unique square name.
     */
    fun squareName (row: Int, col: Int): String {
        return "Square $row, $col"
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
    private fun inDarkMode (context: Context): Boolean {
        val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }
}