package com.pstorli.pokerdice.repo.local

import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.pairNames
import com.pstorli.pokerdice.repo.PokerDS
import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.CASH_BORDER_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE0_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE1_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE2_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE3_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE4_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE5_NAME
import com.pstorli.pokerdice.util.Consts.COLOR_DICE6_NAME
import com.pstorli.pokerdice.util.Consts.DICE_ZERO
import com.pstorli.pokerdice.util.Consts.LEVEL_NAME
import com.pstorli.pokerdice.util.Consts.squareName
import com.pstorli.pokerdice.util.Persist
import com.pstorli.pokerdice.util.Prefs

/**
 * This class saves and loads values from preferences.
 * Actual save / load functions that use editor and prefs
 * at bottom as primitives.
 *
 * Other functions, use primitive functions at bottom to load/save larger classes.
 */
class PokerPrefs (var prefs: Prefs) : PokerDS {

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The whole enchilada!
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load the dao object that has all our state.
     *
     * @param what - Array of Persist items to load, or Persist.ALL
     */
    override suspend fun loadDAO (what: Array<Persist>): PokerDAO
    {
        // Loop through items to load.
        val pokerDAO = PokerDAO ()
        for (which in what) {
            when (which) {
                Persist.CASH                -> pokerDAO.cash            = prefs.getInt        (CASH_BORDER_NAME)
                Persist.COLOR_DICE0         -> pokerDAO.colorDice0      = prefs.getColorPair  (COLOR_DICE0_NAME.pairNames ())
                Persist.COLOR_DICE1         -> pokerDAO.colorDice1      = prefs.getColorPair  (COLOR_DICE1_NAME.pairNames ())
                Persist.COLOR_DICE2         -> pokerDAO.colorDice2      = prefs.getColorPair  (COLOR_DICE2_NAME.pairNames ())
                Persist.COLOR_DICE3         -> pokerDAO.colorDice3      = prefs.getColorPair  (COLOR_DICE3_NAME.pairNames ())
                Persist.COLOR_DICE4         -> pokerDAO.colorDice4      = prefs.getColorPair  (COLOR_DICE4_NAME.pairNames ())
                Persist.COLOR_DICE5         -> pokerDAO.colorDice5      = prefs.getColorPair  (COLOR_DICE5_NAME.pairNames ())
                Persist.COLOR_DICE6         -> pokerDAO.colorDice6      = prefs.getColorPair  (COLOR_DICE6_NAME.pairNames ())
                Persist.LEVEL               -> pokerDAO.level           = prefs.getInt        (LEVEL_NAME)
            }
        }
        return pokerDAO
    }

    /**
     * Save the dao object that has all our state.
     *
     * @param what - Array of Persist items to save, or Persist.ALL
     */
    override suspend fun saveDAO (pokerDAO: PokerDAO, what: Array<Persist>)
    {
        // Loop through items to save.
        for (which in what) {
            when (which) {
                Persist.CASH                -> prefs.putInt       (CASH_BORDER_NAME,                    pokerDAO.cash)
                Persist.COLOR_DICE0         -> prefs.putColorPair (COLOR_DICE0_NAME.pairNames (),       pokerDAO.colorDice0)
                Persist.COLOR_DICE1         -> prefs.putColorPair (COLOR_DICE1_NAME.pairNames (),       pokerDAO.colorDice1)
                Persist.COLOR_DICE2         -> prefs.putColorPair (COLOR_DICE2_NAME.pairNames (),       pokerDAO.colorDice2)
                Persist.COLOR_DICE3         -> prefs.putColorPair (COLOR_DICE3_NAME.pairNames (),       pokerDAO.colorDice3)
                Persist.COLOR_DICE4         -> prefs.putColorPair (COLOR_DICE4_NAME.pairNames (),       pokerDAO.colorDice4)
                Persist.COLOR_DICE5         -> prefs.putColorPair (COLOR_DICE5_NAME.pairNames (),       pokerDAO.colorDice5)
                Persist.COLOR_DICE6         -> prefs.putColorPair (COLOR_DICE6_NAME.pairNames (),       pokerDAO.colorDice6)
                Persist.LEVEL               -> prefs.putInt       (LEVEL_NAME,                          pokerDAO.level)
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Reset, get or set the board.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Reset the board.
     */
    @Suppress("unused")
    fun resetBoard () {
        // Loop through the rows and cols.
        for (index in 0 until BOARD_SIZE*BOARD_SIZE) {
            prefs.putInt (squareName(index), DICE_ZERO)
        }
    }
}