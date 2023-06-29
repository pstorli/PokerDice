package com.pstorli.pokerdice.ui.theme

import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.util.Consts
import com.pstorli.pokerdice.util.Consts.SUIT_SPADE
import com.pstorli.pokerdice.util.Consts.SUIT_HEART
import com.pstorli.pokerdice.util.Consts.SUIT_CLUB
import com.pstorli.pokerdice.util.Consts.SUIT_DIAMOND
import com.pstorli.pokerdice.util.Consts.SUIT_NONE

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
val COLOR_DK_SUIT_NONE          = Color.White
val COLOR_DK_SUIT_HEART         = Color.Red
val COLOR_DK_SUIT_DIAMOND       = Color.Yellow
val COLOR_DK_SUIT_CLUB          = Color.Blue
val COLOR_DK_SUIT_SPADE         = Color.Green

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
val COLOR_LT_SUIT_NONE          = Color.White
val COLOR_LT_SUIT_HEART         = Color.Red
val COLOR_LT_SUIT_DIAMOND       = Color.Yellow
val COLOR_LT_SUIT_CLUB          = Color.Blue
val COLOR_LT_SUIT_SPADE         = Color.Green

val COLOR_LT_HELD_BORDER        = Pink
val COLOR_LT_OUT_TEXT           = Color.Black
val COLOR_LT_ROLL_DICE          = Color.Red
val COLOR_LT_SELECTED           = Color.Red
val COLOR_LT_TEXT               = Color.Black
val COLOR_LT_TITLE              = Color.Blue

var DICE_LT_SUIT_NONE           = COLOR_LT_SUIT_NONE
var DICE_LT_SUIT_HEART          = COLOR_LT_SUIT_HEART
var DICE_LT_SUIT_DIAMOND        = COLOR_LT_SUIT_DIAMOND
var DICE_LT_SUIT_CLUB           = COLOR_LT_SUIT_CLUB
var DICE_LT_SUIT_SPADE          = COLOR_LT_SUIT_SPADE

var DICE_DK_SUIT_NONE           = COLOR_DK_SUIT_NONE
var DICE_DK_SUIT_HEART          = COLOR_DK_SUIT_HEART
var DICE_DK_SUIT_DIAMOND        = COLOR_DK_SUIT_DIAMOND
var DICE_DK_SUIT_CLUB           = COLOR_DK_SUIT_CLUB
var DICE_DK_SUIT_SPADE          = COLOR_DK_SUIT_SPADE

/**
 * Get the dice suit Color.
 */
fun getColor (suit: Int, darkMode: Boolean=false): Color {
    var color = Color.Black
    when (suit) {
        SUIT_NONE       -> if (darkMode) color = DICE_DK_SUIT_NONE      else color = DICE_LT_SUIT_NONE
        SUIT_HEART      -> if (darkMode) color = DICE_DK_SUIT_HEART     else color = DICE_LT_SUIT_HEART
        SUIT_DIAMOND    -> if (darkMode) color = DICE_DK_SUIT_DIAMOND   else color = DICE_LT_SUIT_DIAMOND
        SUIT_CLUB       -> if (darkMode) color = DICE_DK_SUIT_CLUB      else color = DICE_LT_SUIT_CLUB
        SUIT_SPADE      -> if (darkMode) color = DICE_DK_SUIT_SPADE     else color = DICE_LT_SUIT_SPADE
    }
    Consts.debug("getColor $suit $darkMode"+color.toString())
    return color
}

/**
 * Set the dice getColor.
 */
fun setColor (num: Int, color: Color, darkMode: Boolean=false) {
    when (num) {
        SUIT_NONE       -> if (darkMode) DICE_DK_SUIT_NONE      = color else DICE_LT_SUIT_NONE      = color
        SUIT_HEART      -> if (darkMode) DICE_DK_SUIT_HEART     = color else DICE_LT_SUIT_HEART     = color
        SUIT_DIAMOND    -> if (darkMode) DICE_DK_SUIT_DIAMOND   = color else DICE_LT_SUIT_DIAMOND   = color
        SUIT_CLUB       -> if (darkMode) DICE_DK_SUIT_CLUB      = color else DICE_LT_SUIT_CLUB      = color
        SUIT_SPADE      -> if (darkMode) DICE_DK_SUIT_SPADE     = color else DICE_LT_SUIT_SPADE     = color
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
        Colors.Btn              -> if (darkMode) color = COLOR_DK_CASH_OUT          else color = COLOR_LT_CASH_OUT
        Colors.Edge             -> if (darkMode) color = COLOR_DK_EDGE              else color = COLOR_LT_EDGE
        Colors.Held             -> if (darkMode) color = COLOR_DK_HELD_BORDER       else color = COLOR_LT_HELD_BORDER
        Colors.Out              -> if (darkMode) color = COLOR_DK_OUT_TEXT          else color = COLOR_LT_OUT_TEXT
        Colors.Wild             -> if (darkMode) color = COLOR_DK_ROLL_DICE         else color = COLOR_LT_ROLL_DICE
        Colors.Selected         -> if (darkMode) color = COLOR_DK_SELECTED          else color = COLOR_LT_SELECTED
        Colors.Suit_None        -> if (darkMode) color = COLOR_LT_SUIT_NONE         else color = COLOR_DK_SUIT_NONE
        Colors.Suit_Heart       -> if (darkMode) color = COLOR_LT_SUIT_HEART        else color = COLOR_DK_SUIT_HEART
        Colors.Suit_Diamond     -> if (darkMode) color = COLOR_LT_SUIT_DIAMOND      else color = COLOR_DK_SUIT_DIAMOND
        Colors.Suit_Club        -> if (darkMode) color = COLOR_LT_SUIT_CLUB         else color = COLOR_DK_SUIT_CLUB
        Colors.Suit_Spade       -> if (darkMode) color = COLOR_LT_SUIT_SPADE        else color = COLOR_DK_SUIT_SPADE
        Colors.Text             -> if (darkMode) color = COLOR_DK_TEXT              else color = COLOR_LT_TEXT
        Colors.Title            -> if (darkMode) color = COLOR_DK_TITLE             else color = COLOR_LT_TITLE
    }
    Consts.debug("getColor $name $darkMode"+color.toString())
    return color
}

fun resetColors () {
    DICE_LT_SUIT_NONE       = COLOR_LT_SUIT_NONE
    DICE_LT_SUIT_HEART      = COLOR_LT_SUIT_HEART
    DICE_LT_SUIT_DIAMOND    = COLOR_LT_SUIT_DIAMOND
    DICE_LT_SUIT_CLUB       = COLOR_LT_SUIT_CLUB
    DICE_LT_SUIT_SPADE      = COLOR_LT_SUIT_SPADE

    DICE_DK_SUIT_NONE       = COLOR_DK_SUIT_NONE
    DICE_DK_SUIT_HEART      = COLOR_DK_SUIT_HEART
    DICE_DK_SUIT_DIAMOND    = COLOR_DK_SUIT_DIAMOND
    DICE_DK_SUIT_CLUB       = COLOR_DK_SUIT_CLUB
    DICE_DK_SUIT_SPADE      = COLOR_DK_SUIT_SPADE
}
