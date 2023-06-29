/*
 * Add our extension functions here.
 */

package com.pstorli.pokerdice

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.model.Die
import com.pstorli.pokerdice.domain.model.MutablePair
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.ui.theme.getColor
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.FLUSH
import com.pstorli.pokerdice.util.Consts.FOUR_OF_KIND
import com.pstorli.pokerdice.util.Consts.FULL_HOUSE
import com.pstorli.pokerdice.util.Consts.NOTHING
import com.pstorli.pokerdice.util.Consts.NO_TEXT
import com.pstorli.pokerdice.util.Consts.ONE_PAIR
import com.pstorli.pokerdice.util.Consts.ROYAL_FLUSH
import com.pstorli.pokerdice.util.Consts.STRAIGHT
import com.pstorli.pokerdice.util.Consts.STRAIGHT_FLUSH
import com.pstorli.pokerdice.util.Consts.THREE_OF_KIND
import com.pstorli.pokerdice.util.Consts.TWO_PAIRS

// *********************************************************************************************
// Extension Log helper functions
// *********************************************************************************************

/**
 * Log an error message.
 */
@Suppress("unused")
fun Exception.logError ()
{
    Consts.logError (this.toString())
}

/**
 * Log an error message.
 */
@Suppress("unused")
fun String.logError (t: Throwable? = null)
{
    Consts.logError (this, t)
}

/**
 * Log an error message.
 */
@Suppress("unused")
fun String.logError ()
{
    Consts.logError (this)
}

/**
 * Log an error message.
 */
@Suppress("unused")
fun String.logError (tag: String)
{
    Consts.logError (tag, this)
}

/**
 * Log an error message.
 */
@Suppress("unused")
fun String.logWarning ()
{
    Consts.logWarning ( this)
}

/**
 * Log an error message.
 */
@Suppress("unused")
fun String.logWarning (tag: String)
{
    Consts.logWarning (tag, this)
}

/**
 * Log an info message.
 */
fun String.logInfo ()
{
    Consts.logInfo (this)
}

/**
 * Log an info message.
 */
@Suppress("unused")
fun String.logInfo (tag: String)
{
    Consts.logInfo (tag, this)
}

/**
 * Log an info message.
 */
fun String.debug ()
{
    Consts.debug (this)
}
/**
 * Log an info message.
 */
fun String.empty (): Boolean
{
    return 0 == this.length
}

// *********************************************************************************************
// More helper functions
// *********************************************************************************************

/**
 * Set night/dark mode.
 */
fun Context.setDarkMode (state: Boolean) {
    val uiManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    if (state) {
        uiManager.nightMode = UiModeManager.MODE_NIGHT_YES
    } else {
        uiManager.nightMode = UiModeManager.MODE_NIGHT_NO
    }
}

/**
 * Toggle night/dark mode?
 */
fun Context.toggleDarkMode () {
    setDarkMode (inDarkMode ())
}

/**
 * Are we in night/dark mode?
 */
fun Context.inDarkMode (): Boolean {
    val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
}

/**
 * return a pair from the mutable pair.
 */
fun String.pairNames (): MutablePair<String, String> {
    return MutablePair (this+" Dark", this)
}
/**
 * Get a color.
 */
fun Context.color (name: Colors): Color {
    return getColor (name, inDarkMode ())
}

/**
 * Get a color.
 */
fun Application.color (name: Colors): Color {
    return getColor (name, inDarkMode ())
}

/**
 * Get the dice getColor.
 */
fun Application.color (die: Die): Color {
    return getColor (die.rank, inDarkMode ())
}

/**
 * Get the dice getColor.
 */
fun Context.color (die: Die): Color {
    return getColor (die.rank, inDarkMode ())
}

/**
 * Add a modifier conditionally.
 */
fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

/**
 * Get the resource id for this dice.
 * The dice image.
 */
fun Die.color (darkMode: Boolean): Color {
    return getColor(suit, darkMode)
}

/**
 * Get the resource id for this dice.
 * The dice image.
 */
fun Die.rankId (): Int {
    var result = R.drawable.none
    when (rank) {
        Consts.RANK_NONE    -> result = R.drawable.none
        Consts.RANK_ONE     -> result = R.drawable.one
        Consts.RANK_TWO     -> result = R.drawable.two
        Consts.RANK_THREE   -> result = R.drawable.three
        Consts.RANK_FOUR    -> result = R.drawable.four
        Consts.RANK_FIVE    -> result = R.drawable.five
        Consts.RANK_SIX     -> result = R.drawable.six
    }
    return result
}

/**
 * Get the resource id for this dice.
 * The dice image.
 */
fun Die.suitId (): Int {
    var result = R.drawable.none
    when (suit) {
        Consts.SUIT_NONE    -> result = R.drawable.none
        Consts.SUIT_HEART   -> result = R.drawable.heart
        Consts.SUIT_DIAMOND -> result = R.drawable.diamond
        Consts.SUIT_CLUB    -> result = R.drawable.club
        Consts.SUIT_SPADE   -> result = R.drawable.spade
    }
    return result
}

/**
 * Get the resource id for this dice.
 * The dice image.
 */
fun Context.suitName (die: Die): String {
    var result = NO_TEXT
    when (die.suit) {
        Consts.SUIT_HEART   -> result = resources.getString(R.string.suit_heart)
        Consts.SUIT_DIAMOND -> result = resources.getString(R.string.suit_diamond)
        Consts.SUIT_CLUB    -> result = resources.getString(R.string.suit_club)
        Consts.SUIT_SPADE   -> result = resources.getString(R.string.suit_spade)
    }
    return result
}

/**
 * Get the hand to beat name.
 */
fun Context.getHandToBeatName (hand: Int): String {
    var result = NO_TEXT
    when (hand) {
        ROYAL_FLUSH     -> result = resources.getString(R.string.hand_royal_flush)
        STRAIGHT_FLUSH  -> result = resources.getString(R.string.hand_straight_flush)
        FOUR_OF_KIND    -> result = resources.getString(R.string.hand_4_of_kind)
        FULL_HOUSE      -> result = resources.getString(R.string.hand_full_house)
        FLUSH           -> result = resources.getString(R.string.hand_flush)
        STRAIGHT        -> result = resources.getString(R.string.hand_straight)
        THREE_OF_KIND   -> result = resources.getString(R.string.hand_3_of_kind)
        TWO_PAIRS       -> result = resources.getString(R.string.hand_two_pair)
        ONE_PAIR        -> result = resources.getString(R.string.hand_one_pair)
        NOTHING         -> result = resources.getString(R.string.hand_highest)
    }
    result.debug()
    return result
}






