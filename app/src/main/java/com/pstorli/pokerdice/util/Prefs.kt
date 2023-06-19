package com.pstorli.pokerdice.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.model.MutablePair

class Prefs (application: Application) {
    // *********************************************************************************************
    // PokerDS Vars
    // *********************************************************************************************
    val PREFERENCE_FILE_NAME = "Poker.prefs"

    val pref: SharedPreferences = application.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    val editor = pref.edit()

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Get/Set Color prefs.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a getColor pair value.
     */
    fun getColorPair (name: MutablePair<String, String>): MutablePair <Color, Color> {
        // Return the RGB
        return MutablePair (getColor (name.first), getColor (name.second))
    }

    /**
     * Save a getColor value.
     */
    fun putColorPair (name: MutablePair<String, String>, value: MutablePair <Color, Color>) {
        putColor(name.first,  value.first)
        putColor(name.second, value.second)
    }

    /**
     * Fetch a getColor value.
     */
    fun getColor (name: String): Color {
        // Return the RGB
        return Color (
            getFloat (name+ Consts.RED_NAME),
            getFloat (name+ Consts.GREEN_NAME),
            getFloat (name+ Consts.BLUE_NAME))
    }

    /**
     * Save a getColor value.
     */
    fun putColor (name: String, value: Color) {
        putFloat (name+ Consts.RED_NAME,     value.red)
        putFloat (name+ Consts.GREEN_NAME,   value.green)
        putFloat (name+ Consts.BLUE_NAME,    value.blue)
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Get/Set Primitives (Int, String, Float, etc.) prefs. Uses pref and editor
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch a string value.
     */
    @Suppress("unused")
    fun get (name: String): String {
        return pref.getString(name, "")!!
    }

    /**
     * Save a string.
     */
    @Suppress("unused")
    fun put (name: String, value: String) {
        editor.putString(name, value)
        editor.commit()
    }

    /**
     * Fetch a boolean value.
     */
    fun getBool (name: String): Boolean {
        return pref.getBoolean(name, false)
    }

    /**
     * Save a boolean value.
     */
    fun putBool (name: String, value: Boolean) {
        editor.putBoolean(name, value)
        editor.commit()
    }

    /**
     * Fetch an int value.
     */
    fun getInt (name: String): Int {
        return pref.getInt(name, 0)
    }

    /**
     * Save an int.
     */
    fun putInt (name: String, value: Int) {
        editor.putInt(name, value)
        editor.commit()
    }

    /**
     * Fetch an int value.
     */
    fun getFloat (name: String): Float {
        return pref.getFloat(name, 1.0F)
    }

    /**
     * Save a string.
     */
    fun putFloat (name: String, value: Float) {
        editor.putFloat(name, value)
        editor.commit()
    }
}