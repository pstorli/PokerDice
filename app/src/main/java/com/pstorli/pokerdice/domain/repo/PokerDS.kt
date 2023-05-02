package com.pstorli.pokerdice.repo

import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.util.Persist

interface PokerDS {

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The whole enchilada!
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load the dao object that has all our state.
     *
     * @param what - Array of Persist items to load, or Persist.ALL
     */
    suspend fun loadDAO (what: Array<Persist> = Persist.values()): PokerDAO

    /**
     * Save the dao object that has all our state.
     *
     * @param what - Array of Persist items to save, or Persist.ALL
     */
    suspend fun saveDAO (pokerDAO: PokerDAO, what: Array<Persist> = Persist.values())

}