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
    return getColor (die.num, inDarkMode ())
}

/**
 * Get the dice getColor.
 */
fun Context.color (die: Die): Color {
    return getColor (die.num, inDarkMode ())
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




