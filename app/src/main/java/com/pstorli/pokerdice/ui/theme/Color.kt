package com.pstorli.pokerdice.ui.theme

import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.DICE_FIVE
import com.pstorli.pokerdice.util.Consts.DICE_FOUR
import com.pstorli.pokerdice.util.Consts.DICE_ONE
import com.pstorli.pokerdice.util.Consts.DICE_SIX
import com.pstorli.pokerdice.util.Consts.DICE_THREE
import com.pstorli.pokerdice.util.Consts.DICE_TWO
import com.pstorli.pokerdice.util.Consts.DICE_ZERO

// *********************************************************************************************
// Custom Colors
// *********************************************************************************************

val Purple80                    = Color(0xFFD0BCFF)
val PurpleGrey80                = Color(0xFFCCC2DC)
val Pink80                      = Color(0xFFEFB8C8)

val Purple40                    = Color(0xFF6650a4)
val PurpleGrey40                = Color(0xFF625b71)
val Pink40                      = Color(0xFF7D5260)

val DarkGreen                   = Color(0xFF008000)
val Orange                      = Color(0xFFF78621)
val Pink                        = Color(0xFFFF00FF)
val Silver                      = Color(0xFFC0C0C0)
val PaleWhite                   = Color(0xFFDFDFDF)

// *********************************************************************************************
// Dark Colors
// *********************************************************************************************

// Named colors.
val COLOR_DK_BACK               = PaleWhite
val COLOR_DK_BORDER             = Color.DarkGray
val COLOR_DK_CASH_OUT           = DarkGreen

// The default dark colors.
val COLOR_DK_DICE0              = Color.White
val COLOR_DK_DICE1              = Color.Blue
val COLOR_DK_DICE2              = Color.Cyan
val COLOR_DK_DICE3              = Color.Red
val COLOR_DK_DICE4              = Orange
val COLOR_DK_DICE5              = Color.Magenta
val COLOR_DK_DICE6              = Color.Yellow

val COLOR_DK_EDGE               = Color.Black
val COLOR_DK_HELD_BORDER        = Pink
val COLOR_DK_OUT_TEXT           = Color.White
val COLOR_DK_ROLL_DICE          = Color.Red
val COLOR_DK_SELECTED           = Color.Red
val COLOR_DK_TEXT               = Color.White
val COLOR_DK_TITLE              = Color.Blue

// *********************************************************************************************
// Light Colors
// *********************************************************************************************

val COLOR_LT_BACK               = PaleWhite
val COLOR_LT_BORDER             = Color.DarkGray
val COLOR_LT_CASH_OUT           = DarkGreen
val COLOR_LT_EDGE               = Color.White

// The default light colors.
val COLOR_LT_DICE0              = Color.Black
val COLOR_LT_DICE1              = Color.Blue
val COLOR_LT_DICE2              = Color.Cyan
val COLOR_LT_DICE3              = Color.Red
val COLOR_LT_DICE4              = Orange
val COLOR_LT_DICE5              = Color.Magenta
val COLOR_LT_DICE6              = Color.Yellow

val COLOR_LT_HELD_BORDER        = Pink
val COLOR_LT_OUT_TEXT           = Color.Black
val COLOR_LT_ROLL_DICE          = Color.Red
val COLOR_LT_SELECTED           = Color.Red
val COLOR_LT_TEXT               = Color.Blue
val COLOR_LT_TITLE              = Color.Blue

var DICE_LT_ZERO                = COLOR_LT_DICE0
var DICE_LT_ONE                 = COLOR_LT_DICE1
var DICE_LT_TWO                 = COLOR_LT_DICE2
var DICE_LT_THREE               = COLOR_LT_DICE3
var DICE_LT_FOUR                = COLOR_LT_DICE4
var DICE_LT_FIVE                = COLOR_LT_DICE5
var DICE_LT_SIX                 = COLOR_LT_DICE6

var DICE_DK_ZERO                = COLOR_DK_DICE0
var DICE_DK_ONE                 = COLOR_DK_DICE1
var DICE_DK_TWO                 = COLOR_DK_DICE2
var DICE_DK_THREE               = COLOR_DK_DICE3
var DICE_DK_FOUR                = COLOR_DK_DICE4
var DICE_DK_FIVE                = COLOR_DK_DICE5
var DICE_DK_SIX                 = COLOR_DK_DICE6

/**
 * Get the dice getColor.
 */
fun getColor (num: Int, darkMode: Boolean=false): Color {
    var color = Color.White
    when (num) {
        DICE_ZERO   -> if (darkMode) color = DICE_DK_ZERO   else color = DICE_LT_ZERO
        DICE_ONE    -> if (darkMode) color = DICE_DK_ONE    else color = DICE_LT_ONE
        DICE_TWO    -> if (darkMode) color = DICE_DK_TWO    else color = DICE_LT_TWO
        DICE_THREE  -> if (darkMode) color = DICE_DK_THREE  else color = DICE_LT_THREE
        DICE_FOUR   -> if (darkMode) color = DICE_DK_FOUR   else color = DICE_LT_FOUR
        DICE_FIVE   -> if (darkMode) color = DICE_DK_FIVE   else color = DICE_LT_FIVE
        DICE_SIX    -> if (darkMode) color = DICE_DK_SIX    else color = DICE_LT_SIX
    }
    Consts.debug("getColor $num $darkMode"+color.toString())
    return color
}

/**
 * Set the dice getColor.
 */
fun setColor (num: Int, color: Color, darkMode: Boolean=false) {
    when (num) {
        DICE_ZERO   -> if (darkMode) DICE_DK_ZERO   = color else DICE_LT_ZERO   = color
        DICE_ONE    -> if (darkMode) DICE_DK_ONE    = color else DICE_LT_ONE    = color
        DICE_TWO    -> if (darkMode) DICE_DK_TWO    = color else DICE_LT_TWO    = color
        DICE_THREE  -> if (darkMode) DICE_DK_THREE  = color else DICE_LT_THREE  = color
        DICE_FOUR   -> if (darkMode) DICE_DK_FOUR   = color else DICE_LT_FOUR   = color
        DICE_FIVE   -> if (darkMode) DICE_DK_FIVE   = color else DICE_LT_FIVE   = color
        DICE_SIX    -> if (darkMode) DICE_DK_SIX    = color else DICE_LT_SIX    = color
    }
    Consts.debug("setColor $num $darkMode"+color.toString())
}

/**
 * Get a named getColor.
 */
fun getColor (name: Colors, darkMode: Boolean=false): Color {
    var color = Color.White
    when (name) {
        Colors.Back             -> if (darkMode) color = COLOR_DK_BACK              else color = COLOR_LT_BACK
        Colors.Border           -> if (darkMode) color = COLOR_DK_BORDER            else color = COLOR_LT_BORDER
        Colors.CashOut          -> if (darkMode) color = COLOR_DK_CASH_OUT          else color = COLOR_LT_CASH_OUT
        Colors.Dice0            -> if (darkMode) color = COLOR_LT_DICE0             else color = COLOR_DK_DICE0
        Colors.Dice1            -> if (darkMode) color = COLOR_LT_DICE1             else color = COLOR_DK_DICE1
        Colors.Dice2            -> if (darkMode) color = COLOR_LT_DICE2             else color = COLOR_DK_DICE2
        Colors.Dice3            -> if (darkMode) color = COLOR_LT_DICE3             else color = COLOR_DK_DICE3
        Colors.Dice4            -> if (darkMode) color = COLOR_LT_DICE4             else color = COLOR_DK_DICE4
        Colors.Dice5            -> if (darkMode) color = COLOR_LT_DICE5             else color = COLOR_DK_DICE5
        Colors.Dice6            -> if (darkMode) color = COLOR_LT_DICE6             else color = COLOR_DK_DICE6
        Colors.Edge             -> if (darkMode) color = COLOR_DK_EDGE              else color = COLOR_LT_EDGE
        Colors.Held             -> if (darkMode) color = COLOR_DK_HELD_BORDER       else color = COLOR_LT_HELD_BORDER
        Colors.Out              -> if (darkMode) color = COLOR_DK_OUT_TEXT          else color = COLOR_LT_OUT_TEXT
        Colors.RollDice         -> if (darkMode) color = COLOR_DK_ROLL_DICE         else color = COLOR_LT_ROLL_DICE
        Colors.Selected         -> if (darkMode) color = COLOR_DK_SELECTED          else color = COLOR_LT_SELECTED
        Colors.Text             -> if (darkMode) color = COLOR_DK_TEXT              else color = COLOR_LT_TEXT
        Colors.Title            -> if (darkMode) color = COLOR_DK_TITLE             else color = COLOR_LT_TITLE
    }
    Consts.debug("getColor $name $darkMode"+color.toString())
    return color
}

fun resetColors () {

}
