package com.pstorli.pokerdice.util

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
    val BET_NAME                        = "Bet"
    val BET_MIN_WIDTH                   = 36.dp
    val BORDER_DEFAULT_WIDTH_VAL        = 2.dp
    val BLUE_NAME                       = "Blue"
    val BOARD_SIZE                      = 7
    val BOARD_FIRST                     = 0
    val BOARD_LAST                      = BOARD_SIZE-1
    val CASH_BORDER_NAME                = "Cash"
    val CASH_INITIAL                    = 100
    val CASH_MIN_WIDTH                  = 48.dp
    val DEFAULT_ELEVATION_VAL           = 10.dp
    val DIAMOND_MIN_WIDTH               = 96.dp

    val RANK_NONE                       = 0
    val RANK_ONE                        = 1
    val RANK_TWO                        = 2
    val RANK_THREE                      = 3
    val RANK_FOUR                       = 4
    val RANK_FIVE                       = 5
    val RANK_SIX                        = 6

    val SUIT_NONE                       = 0
    val SUIT_HEART                      = 1
    val SUIT_DIAMOND                    = 2
    val SUIT_CLUB                       = 3
    val SUIT_SPADE                      = 4

    // Color pairs.
    val SUIT_COLOR_NONE                 = "Suit Color None"
    val SUIT_COLOR_HEART                = "Suit Color Heart"
    val SUIT_COLOR_DIAMOND              = "Suit Color Diamond"
    val SUIT_COLOR_CLUB                 = "Suit Color Club"
    val SUIT_COLOR_SPADE                = "Suit Color Spade"

    val DISABLED_ELEVATION_VAL          = 0.dp
    val HAND_TO_BEAT_DICE_SIZE          = 48.dp
    val HAND_TO_BEAT_SIZE               = 5
    val NO_TEXT                         = ""
    val GAME_SAVED                      = "Game saved"
    val GREEN_NAME                      = "Green"
    val HUNDRED_VAL                     = 100
    val LEVEL_NAME                      = "Level"
    val LEVEL_MIN_WIDTH                 = 54.dp
    val POKER_BORDER_SIZE               = 2.dp
    val POKER_BTN_MIN_WIDTH             = 56.dp
    val PRESSED_ELEVATION_VAL           = 15.dp
    val RED_NAME                        = "Red"
    val ROLLS_BORDER_NAME               = "Rolls"
    val ROLLS_MAX                       = 3
    val ROLLS_MIN_WIDTH                 = 48.dp
    val ROUNDED_CORNER_SHAPE_PCT_VAL    = 20
    val TAG_NAME                        = "PokerDice"
    val WON_MIN_WIDTH                   = 42.dp
    val WON_NAME                        = "Won"
    val SUIT_NONE_VAL                   = 0

    // *********************************************************************************************
    // More helper functions
    // *********************************************************************************************

    /**
     * Is this a edge sqaure?
     */
    fun isEdgeSquare (index: Int): Boolean {
        val rowCol = rowCol (index)
        return if (0 == rowCol.first || BOARD_LAST == rowCol.first || 0 == rowCol.second || BOARD_LAST == rowCol.second)
            true else false
    }

    /**
     * Deterine the row,col from the index.
     */
    fun row (index: Int): Int {
        return index / BOARD_SIZE
    }

    /**
     * Deterine the row,col from the index.
     */
    fun col (index: Int): Int {
        return index % BOARD_SIZE
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
        return row * BOARD_SIZE + col
    }

    /**
     * Given a row/col create a unique square name.
     */
    fun squareName (index: Int): String {
        return "Square $index"
    }

    /**
     * Return random num between 1 and 6
     */
    fun randomRank (): Int {
        val num = (RANK_ONE..RANK_SIX).random()
        debug("random () $num")
        return num
    }

    /**
     * Return random num between 1 and 6
     */
    fun randomSuit (): Int {
        val num = (SUIT_HEART..SUIT_SPADE).random()
        debug("random () $num")
        return num
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