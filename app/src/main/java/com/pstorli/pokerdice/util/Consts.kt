package com.pstorli.pokerdice.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pstorli.pokerdice.logError


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
    // *********************************************************************************************
    // Critical Vars
    // *********************************************************************************************
    val BOARD_SIZE                      = 7
    val CASH_INITIAL                    = 100
    val DICE_IN_HAND                    = 5
    val FIRST_ROW_COL                   = 1
    val LAST_ROW_COL                    = 5
    val NUM_SQUARES                     = BOARD_SIZE * BOARD_SIZE
    val ROLLS_MAX                       = 3
    val SQUARE_FIRST                    = 0
    val SQUARE_LAST                     = BOARD_SIZE-1

    // *********************************************************************************************
    // More  Vars
    // *********************************************************************************************
    val BET_NAME                        = "Bet"
    val BET_MIN_WIDTH_DP                = 36.dp
    val BORDER_DEFAULT_WIDTH_VAL_DP     = 2.dp
    val BLUE_NAME                       = "Blue"

    val CASH_BORDER_NAME                = "Cash"
    val CASH_MIN_WIDTH_DP               = 48.dp
    val DEFAULT_ELEVATION_VAL_DP        = 10.dp
    val DIAMOND_MIN_WIDTH_DP            = 96.dp

    val RANK_NONE                       = 0
    val RANK_ONE                        = 1
    val RANK_TWO                        = 2
    val RANK_THREE                      = 3
    val RANK_FOUR                       = 4
    val RANK_FIVE                       = 5
    val RANK_SIX                        = 6

    // Color pairs.
    val SUIT_COLOR_NONE                 = "Suit Color None"
    val SUIT_COLOR_HEART                = "Suit Color Heart"
    val SUIT_COLOR_DIAMOND              = "Suit Color Diamond"
    val SUIT_COLOR_CLUB                 = "Suit Color Club"
    val SUIT_COLOR_SPADE                = "Suit Color Spade"

    val TITLE_HEIGHT_DP                 = 48.dp

    val DISABLED_ELEVATION_VAL_DP       = 0.dp
    val HAND_TO_BEAT_DICE_DP            = 48.dp
    val GAME_SAVED                      = "Game saved"
    val GREEN_NAME                      = "Green"
    val HUNDRED_VAL                     = 100
    val LEVEL_NAME                      = "Level"
    val LEVEL_MIN_WIDTH_DP              = 54.dp
    val NEWLINE                         = '\n'
    val NO_TEXT                         = ""
    val ONE                             = 1
    val POKER_BORDER_SIZE_DP            = 2.dp
    val POKER_BTN_MIN_WIDTH_DP          = 56.dp
    val PRESSED_ELEVATION_VAL_DP        = 15.dp
    val RED_NAME                        = "Red"
    val ROLLS_BORDER_NAME               = "Rolls"
    val ROLLS_MIN_WIDTH_DP              = 48.dp
    val ROUNDED_CORNER_SHAPE_PCT_VAL    = 20
    val SPACE_TEXT                      = " "
    val SQUARE_BET_COST                 = 10 // Cost of eacg square to bet on.
    val TAG_NAME                        = "PokerDice"
    val WIN_LEVEL_MOD                   = 100
    val WON_FREE_STUFF_URL              = "https://sites.google.com/view/pstorlidesigns/home/freegifts4u"
    val WON_MIN_WIDTH_DP                = 42.dp
    val WON_NAME                        = "Won"
    val SUIT_NONE_VAL                   = 0
    val URL_FONT_SIZE_SP                = 16.sp
    val ZERO                            = 0

    // *********************************************************************************************
    // Scoring Consts
    // *********************************************************************************************

    // The score data.
    val ROYAL_FLUSH                     = 60
    val STRAIGHT_FLUSH                  = 50
    val FOUR_OF_KIND                    = 45
    val FULL_HOUSE                      = 40
    val FLUSH                           = 30
    val STRAIGHT                        = 20
    val THREE_OF_KIND                   = 15
    val TWO_PAIRS                       = 10
    val ONE_PAIR                        = 5
    val NOTHING                         = 0

    // *********************************************************************************************
    // Scoring Vars
    // *********************************************************************************************

    // None and the four suits.
    val SUIT_NONE                       = 0 // Array Index 0 = no suit's count
    val SUIT_HEART                      = 1 // Array Index 1 = heart's count
    val SUIT_DIAMOND                    = 2 // Array Index 2 = diamond's count
    val SUIT_CLUB                       = 3 // Array Index 3 = club's count
    val SUIT_SPADE                      = 4 // Array Index 4 = spade's count

    val NO_RANK                         = 0
    val NO_SUIT                         = 0

    val MIN_SUIT                        = 1
    val MIN_RANK                        = 1
    val MAX_SUIT                        = 4
    val MAX_RANK                        = 6

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

    /**
     * Remove any newlines.
     */
    fun removeNewLines (what: String): String
    {
        var result = NO_TEXT
        if (what.length>ZERO) {
            for (pos in ZERO until what.length) {
                if (NEWLINE != what[pos]) {
                    result = result + what[pos]
                }
                else {
                    result = result + SPACE_TEXT
                }
            }
        }
        return result
    }

    /**
     * Play a sound.
     */
    fun playSound (soundRawId: Int, context: Context) {
        try {
            // Create it.
            var mp: MediaPlayer? = MediaPlayer.create (context, soundRawId)
            mp?.let {
                if (mp?.isPlaying?:false) {
                    // Stop, drop and roll.
                    mp?.stop()
                    mp?.release()

                    // Then re-create it.
                    mp = MediaPlayer.create(context, soundRawId)
                }

                // Now start it.
                mp?.start()
            }
        }
        catch (ex: Exception) {
            ex.toString().logError()
        }
    }
}