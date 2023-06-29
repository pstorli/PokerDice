package com.pstorli.pokerdice.repo.local

import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.pairNames
import com.pstorli.pokerdice.repo.PokerDS
import com.pstorli.pokerdice.util.Consts.CASH_BORDER_NAME
import com.pstorli.pokerdice.util.Consts.SUIT_COLOR_NONE
import com.pstorli.pokerdice.util.Consts.SUIT_COLOR_HEART
import com.pstorli.pokerdice.util.Consts.SUIT_COLOR_DIAMOND
import com.pstorli.pokerdice.util.Consts.SUIT_COLOR_CLUB
import com.pstorli.pokerdice.util.Consts.SUIT_COLOR_SPADE
import com.pstorli.pokerdice.util.Consts.LEVEL_NAME
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
                Persist.CASH                -> pokerDAO.cash                = prefs.getInt        (CASH_BORDER_NAME)
                Persist.COLOR_SUIT_NONE     -> pokerDAO.color_suit_none     = prefs.getColorPair  (SUIT_COLOR_NONE.pairNames ())
                Persist.COLOR_SUIT_HEART    -> pokerDAO.color_suit_heart    = prefs.getColorPair  (SUIT_COLOR_HEART.pairNames ())
                Persist.COLOR_SUIT_DIAMOND  -> pokerDAO.color_suit_diamond  = prefs.getColorPair  (SUIT_COLOR_DIAMOND.pairNames ())
                Persist.COLOR_SUIT_CLUB     -> pokerDAO.color_suit_club     = prefs.getColorPair  (SUIT_COLOR_CLUB.pairNames ())
                Persist.COLOR_SUIT_SPADE    -> pokerDAO.color_suit_spade    = prefs.getColorPair  (SUIT_COLOR_SPADE.pairNames ())
                Persist.LEVEL               -> pokerDAO.level               = prefs.getInt        (LEVEL_NAME)
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
                Persist.CASH                -> prefs.putInt       (CASH_BORDER_NAME,                pokerDAO.cash)
                Persist.COLOR_SUIT_NONE     -> prefs.putColorPair (SUIT_COLOR_NONE.pairNames (),    pokerDAO.color_suit_none)
                Persist.COLOR_SUIT_HEART    -> prefs.putColorPair (SUIT_COLOR_HEART.pairNames (),   pokerDAO.color_suit_heart)
                Persist.COLOR_SUIT_DIAMOND  -> prefs.putColorPair (SUIT_COLOR_DIAMOND.pairNames (), pokerDAO.color_suit_diamond)
                Persist.COLOR_SUIT_CLUB     -> prefs.putColorPair (SUIT_COLOR_CLUB.pairNames (),    pokerDAO.color_suit_club)
                Persist.COLOR_SUIT_SPADE    -> prefs.putColorPair (SUIT_COLOR_SPADE.pairNames (),   pokerDAO.color_suit_spade)
                Persist.LEVEL               -> prefs.putInt       (LEVEL_NAME,                      pokerDAO.level)
            }
        }
    }
}