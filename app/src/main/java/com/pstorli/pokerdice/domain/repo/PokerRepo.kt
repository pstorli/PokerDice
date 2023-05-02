package com.pstorli.pokerdice.repo

import android.app.Application
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.repo.local.PokerPrefs
import com.pstorli.pokerdice.util.Persist

/**
 * This class routes requests for data to the correct repository.
 * This class is also a singleton.
 */
class PokerRepo (application: Application): PokerDS {

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The currently selected DataSource, aka the Default Data Source
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // Canidate DS's
    var staticDS    : PokerPrefs    = PokerPrefs (application)

    // The default ds.
    var ds          : PokerDS       = staticDS

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // The whole enchilada!
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load the dao object that has all our state.
     */
    override suspend fun loadDAO (what: Array<Persist>): PokerDAO {
        return ds.loadDAO(what)
    }

    /**
     * Save the dao object that has all our state.
     */
    override suspend fun saveDAO (pokerDAO: PokerDAO, what: Array<Persist>) {
        ds.saveDAO(pokerDAO, what)
    }
}