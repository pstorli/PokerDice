package com.pstorli.pokerdice.model

import androidx.lifecycle.viewModelScope

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.pstorli.pokerdice.*
import com.pstorli.pokerdice.repo.PokerRepo

import androidx.compose.ui.graphics.Color
import com.pstorli.pokerdice.domain.model.Dice
import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_DK_DICE6
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE1
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE2
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE3
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE4
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE5
import com.pstorli.pokerdice.ui.theme.COLOR_LT_DICE6
import com.pstorli.pokerdice.ui.viewmodel.PokerEvent
import com.pstorli.pokerdice.util.Consts

import com.pstorli.pokerdice.util.Consts.BOARD_SIZE_VAL
import com.pstorli.pokerdice.util.Consts.GAME_SAVED
import com.pstorli.pokerdice.util.Consts.debug
import com.pstorli.pokerdice.util.Persist
import com.pstorli.pokerdice.util.Prefs

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("unused")
class PokerViewModel (application: Application) : AndroidViewModel(application) {

    // *********************************************************************************************
    // Observerable data.
    // *********************************************************************************************

    // The game board.
    var board       by mutableStateOf<Array<IntArray>>(Array(BOARD_SIZE_VAL) { IntArray(BOARD_SIZE_VAL) })

    // How much cash, rolls and bets have we?
    var bet         by mutableStateOf<Int>(0)
    var cash        by mutableStateOf<Int>(0)

    // The dice colors. Pair with light color and dark color.
    var diceColor1  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE1,COLOR_DK_DICE1))
    var diceColor2  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE2,COLOR_DK_DICE2))
    var diceColor3  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE3,COLOR_DK_DICE3))
    var diceColor4  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE4,COLOR_DK_DICE4))
    var diceColor5  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE5,COLOR_DK_DICE5))
    var diceColor6  by mutableStateOf<Pair<Color,Color>> (Pair (COLOR_LT_DICE6,COLOR_DK_DICE6))

    // Preferences
    var prefs: Prefs

    // How many rolls do we have left.
    var rolls       by mutableStateOf<Int>(0)

    // What hand do we need to beat? Both? ;) Pete
    val handToBeat  = mutableStateOf(arrayOf(Dice.One, Dice.Two, Dice.Three, Dice.Four, Dice.Five))

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // The poker repo is used to save game state information.
    var pokerRepo: PokerRepo

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // First thing is to init the repo.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    init {
        // Init the prefs
        prefs     = Prefs (application)

        // Init the repo
        pokerRepo = PokerRepo(prefs)

        // Lets get this game started!
        if (prefs.getBool(GAME_SAVED)) {
            loadGame()
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Game State
    // /////////////////////////////////////////////////////////////////////////////////////////////
    sealed class PokerUIState {
        object Loading : PokerUIState()
        class Loaded (val data: PokerViewModel) : PokerUIState()
        class Error (val message: String) : PokerUIState()
    }
    
    val _uiState = MutableStateFlow<PokerUIState>(PokerUIState.Loaded (this))
    val uiState: StateFlow<PokerUIState> = _uiState

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Helpful Methods
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get the dice color.
     */
    fun getColor (dice: Dice): Color {
        val darkMode = Consts.inDarkMode(getApplication())
        var color = Color.White
        when (dice) {
            Dice.One    -> if (darkMode) color = diceColor1.second else color = diceColor1.first
            Dice.Two    -> if (darkMode) color = diceColor2.second else color = diceColor2.first
            Dice.Three  -> if (darkMode) color = diceColor3.second else color = diceColor3.first
            Dice.Four   -> if (darkMode) color = diceColor4.second else color = diceColor4.first
            Dice.Five   -> if (darkMode) color = diceColor5.second else color = diceColor5.first
            Dice.Six    -> if (darkMode) color = diceColor6.second else color = diceColor6.first
        }
        debug(color.toString())
        return color
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Load Game
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load game from repository.
     */
    @Suppress("unused")
    fun loadGame() {
        viewModelScope.launch {
            // Get the game, the whole enchilada.
            val gameDeferred = viewModelScope.async(Dispatchers.IO) {
                "Load game started.".logInfo()
                pokerRepo.loadDAO()// Load everything.
            }

            // Wait for it.
            val gameResultDAO = gameDeferred.await()

            // Go back on ui thread.
            withContext(Dispatchers.Main) {
                // Update the model.
                this@PokerViewModel.toModel(gameResultDAO)
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Load / Save the model.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Load the model. (Save PokerDAO to PokerViewModel)
     */
    fun toModel (pokerDAO : PokerDAO, what: Array<Persist> = Persist.values()) {
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

    /**
     * Save the model. (Save PokerViewModel to PokerDAO)
     */
    fun fromModel (what: Array<Persist> = Persist.values()): PokerDAO {
        val pokerDAO = PokerDAO ()

        // Loop through items to save.
        for (which in what) {
            when (which) {
                Persist.BET                 -> pokerDAO.bet         = bet
                Persist.BOARD               -> pokerDAO.board       = board
                Persist.CASH                -> pokerDAO.cash        = cash
                Persist.COLOR_DICE1         -> pokerDAO.colorDice1  = diceColor1
                Persist.COLOR_DICE2         -> pokerDAO.colorDice2  = diceColor3
                Persist.COLOR_DICE3         -> pokerDAO.colorDice3  = diceColor4
                Persist.COLOR_DICE4         -> pokerDAO.colorDice4  = diceColor4
                Persist.COLOR_DICE5         -> pokerDAO.colorDice5  = diceColor5
                Persist.COLOR_DICE6         -> pokerDAO.colorDice6  = diceColor6
                Persist.ROLLS               -> pokerDAO.rolls       = rolls
            }
        }

        return pokerDAO
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

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Handle PokerEvents
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * User did something.
     */
    fun onEvent (pokerEvent: PokerEvent) {
        "onEvent pokerEvent ${pokerEvent}".debug ()
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
    fun betEvent () {

    }

    /**
     * Cash out!
     */
    fun cashOutEvent () {

    }

    /**
     * You gotta know when toi hold em!
     */
    fun holdEvent () {

    }

    /**
     * Roll them bones.
     */
    fun rollEvent () {
        // TODO test
        cash++
    }
}
