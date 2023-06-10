/*
 * Add our extension functions here.
 */

package com.pstorli.pokerdice

import android.app.Application
import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.model.Dice
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
 * Get a color.
 */
fun Context.color (name: Pair <String,String>): Color {
    return Consts.color(name, this)
}

/**
 * Get a color.
 */
fun Application.color (name: Pair <String,String>): Color {
    return Consts.color(name, this)
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
 * Convert a dice value to a resource id.
 */
fun Dice.resId (): Int {
    when (this) {
        Dice.Zero   -> return R.drawable.zero
        Dice.One    -> return R.drawable.one
        Dice.Two    -> return R.drawable.two
        Dice.Three  -> return R.drawable.three
        Dice.Four   -> return R.drawable.four
        Dice.Five   -> return R.drawable.five
        Dice.Six    -> return R.drawable.six
    }
}




