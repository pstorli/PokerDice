package com.pstorli.pokerdice.model

import androidx.lifecycle.viewModelScope

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pstorli.pokerdice.*
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.repo.PokerRepo

import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.ui.theme.COLOR_BACK
import com.pstorli.pokerdice.ui.theme.COLOR_BET_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_DICE6
import com.pstorli.pokerdice.ui.theme.COLOR_HOLD_BORDER
import com.pstorli.pokerdice.ui.theme.COLOR_TEXT
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent

import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Persist

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("unused")
class PokerViewModel (application: Application) : AndroidViewModel(application) {

    // *********************************************************************************************
    // Observerable data.
    // *********************************************************************************************
    private val _whatFlow = MutableSharedFlow<Array<Persist>>()
    val whatFlow: SharedFlow<Array<Persist>> = _whatFlow.asSharedFlow()

    // *********************************************************************************************
    // Data.
    // *********************************************************************************************

    // The game board.
    var board: Array<IntArray> = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }

    // How much cash, rolls and bets have we?
    var backColor:          Color   = COLOR_BACK
    var bet:                Int     = 0
    var betBorderColor:     Color   = COLOR_BET_BORDER
    var btnBorderColor:     Color   = COLOR_BORDER
    var cash:               Int     = 0
    var diceColor1:         Color   = COLOR_DICE1
    var diceColor2:         Color   = COLOR_DICE2
    var diceColor3:         Color   = COLOR_DICE3
    var diceColor4:         Color   = COLOR_DICE4
    var diceColor5:         Color   = COLOR_DICE5
    var diceColor6:         Color   = COLOR_DICE6
    var holdBorderColor:    Color   = COLOR_HOLD_BORDER
    var rolls:              Int     = 0
    var textColor:          Color   = COLOR_TEXT

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // The poker repo is used to save game state information.
    private var pokerRepo: PokerRepo

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // First thing is to init the repo.
    // /////////////////////////////////////////////////////////////////////////////////////////////
    init {
        // Init the repo
        pokerRepo = PokerRepo (application)

        // Lets get this game started!
        loadGame ()
    }

    /**
     * Load game from repository.
     */
    @Suppress("unused")
    private fun loadGame () {
        viewModelScope.launch {
            // Get the game, the whole enchilada.
            val gameDeferred = viewModelScope.async(Dispatchers.IO) {
                "Load game started.".logInfo()
                pokerRepo.loadDAO ()// Load everything.
            }

            // Wait for it.
            val gameResultDAO = gameDeferred.await ()

            // Go back on ui thread.
            withContext (Dispatchers.Main) {
                // Update the model.
                updateModel (gameResultDAO)

                // Then, notify them of what was changed. (Everything has changed!)
                _whatFlow.tryEmit (Persist.values())
            }
        }
    }

    /**
     * Update the viewModel with the pokerDAO based on what?
     */
    private fun updateModel (pokerDAO : PokerDAO, what: Array<Persist> = Persist.values()) {
        // Loop through items to save.
        for (which in what) {
            when (which) {
                Persist.BET                 -> bet              = pokerDAO.bet
                Persist.BOARD               -> board            = pokerDAO.board
                Persist.CASH                -> cash             = pokerDAO.cash
                Persist.COLOR_DICE1         -> diceColor1       = pokerDAO.colorDice1
                Persist.COLOR_DICE2         -> diceColor2       = pokerDAO.colorDice2
                Persist.COLOR_DICE3         -> diceColor3       = pokerDAO.colorDice3
                Persist.COLOR_DICE4         -> diceColor4       = pokerDAO.colorDice4
                Persist.COLOR_DICE5         -> diceColor5       = pokerDAO.colorDice5
                Persist.COLOR_DICE6         -> diceColor6       = pokerDAO.colorDice6
                Persist.ROLLS               -> rolls            = pokerDAO.rolls
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Handle PokerEvents
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * User did something.
     */
    fun onEvent (pokerEvent: PokerEvent) {
        when (pokerEvent) {
            // Place your bets!
            is PokerEvent.BetEvent -> {
                betEvent ()
            }

            // Cash out!
            is PokerEvent.CashOutEvent -> {
                cashOutEvent ()
            }

            // You gotta know when toi hold em!
            is PokerEvent.HoldEvent -> {
                holdEvent ()
            }

            // Roll them bones.
            is PokerEvent.RollEvent -> {
                rollEvent ()
            }
        }
    }

    /**
     * Place your bets!
     */
    private fun betEvent () {

    }

    /**
     * Cash out!
     */
    private fun cashOutEvent () {

    }

    /**
     * You gotta know when toi hold em!
     */
    private fun holdEvent () {

    }

    /**
     * Roll them bones.
     */
    private fun rollEvent () {
        // TODO test
        cash++

        // Notify that cash has changed.
        _whatFlow.tryEmit (arrayOf(Persist.CASH))
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Shutdown
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This method will be called when this ViewModel
     * is no longer used and will be destroyed.
     */
    override fun onCleared() {
        super.onCleared()
    }
}
