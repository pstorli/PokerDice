package com.pstorli.pokerdice.domain.model

import androidx.lifecycle.viewModelScope

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.pstorli.pokerdice.*
import com.pstorli.pokerdice.repo.PokerRepo

import com.pstorli.pokerdice.domain.repo.dao.PokerDAO
import com.pstorli.pokerdice.ui.theme.Colors
import com.pstorli.pokerdice.ui.theme.setColor

import com.pstorli.pokerdice.util.Consts.CASH_INITIAL
import com.pstorli.pokerdice.util.Consts.SUIT_NONE
import com.pstorli.pokerdice.util.Consts.GAME_SAVED
import com.pstorli.pokerdice.util.Consts.DICE_IN_HAND
import com.pstorli.pokerdice.util.Consts.NO_TEXT
import com.pstorli.pokerdice.util.Consts.NUM_SQUARES
import com.pstorli.pokerdice.util.Consts.ROLLS_MAX
import com.pstorli.pokerdice.util.Consts.SUIT_NONE_VAL
import com.pstorli.pokerdice.util.Consts.ZERO
import com.pstorli.pokerdice.util.Consts.debug
import com.pstorli.pokerdice.util.Consts.randomRank
import com.pstorli.pokerdice.util.Consts.randomSuit
import com.pstorli.pokerdice.util.Persist
import com.pstorli.pokerdice.util.Prefs

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("unused")
class PokerViewModel (val app: Application) : AndroidViewModel(app) {

    // *********************************************************************************************
    // Observerable data.
    // *********************************************************************************************

    // The game board, 7X7 has A list of 49 Dice items
    // Use these vars to update the board or edge of the board.
    var onUpdateBoard           = mutableStateOf(true)
    var onUpdateBoardEdge       = mutableStateOf(true)
    var onUpdateHandToBeat      = mutableStateOf(true)
    var onUpdateInstructions    = mutableStateOf(true)
    var onUpdatePlayer          = mutableStateOf(true)
    var onUpdateScoring         = mutableStateOf(true)

    // How much cash, rolls and bets have we?
    var bet                     by mutableStateOf<Int>(0)
    var cash                    by mutableStateOf<Int>(0)

    // How many rolls do we have left.
    var rolls                   by mutableStateOf<Int>(0)

    // What hand do we need to beat? Both? ;) Pete
    val handToBeat              = mutableStateOf(Array<Die>(DICE_IN_HAND){Die()})

    // What level are we on?
    var level                   by mutableStateOf<Int>(0)

    // How much have we won?
    var won                     by mutableStateOf<Int>(0)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // Preferences
    var prefs: Prefs

    // The poker repo is used to save game state information.
    var pokerRepo: PokerRepo

    // Used to score the game.
    var pokerScorer = PokerScorer ()

    // The game with a board
    var game = Game ()

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Game State
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * TODO: Comsider using an enum instad of a seled class.
     */
    sealed class PokerUIState {
        object Loading                      : PokerUIState()

        object Start                        : PokerUIState()

        object Rolling                      : PokerUIState()

        object Settings                     : PokerUIState()

        class Error (val message: String)   : PokerUIState()
    }

    var _uiState = MutableStateFlow<PokerUIState>(PokerUIState.Start)
    var uiState: StateFlow<PokerUIState> = _uiState

    /**
     * What state are we in.
     */
    fun getState (): PokerUIState
    {
        return _uiState.value
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // First thing is to init the repo.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    init {
        // Init the prefs
        prefs     = Prefs (app)

        // Init the repo
        pokerRepo = PokerRepo(prefs)

        // Lets get this game started!
        if (prefs.getBool(GAME_SAVED)) {
            loadGame()
        }
        else {
            // Give them cash to start out with.
            cash = CASH_INITIAL
        }

        resetEvent (PokerEvent.ResetEvent)
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
    // Helpful Methods
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get the instructions based on the game state.
     */
    fun getInstructions (): String {
        var result = app.resources.getString(R.string.state_error)
        when (_uiState.value) {
            is PokerUIState.Error       -> result = app.resources.getString(R.string.state_error)
            is PokerUIState.Start       -> result = app.resources.getString(R.string.state_start)
            is PokerUIState.Loading     -> result = app.resources.getString(R.string.state_loading)
            is PokerUIState.Rolling     -> result = app.resources.getString(R.string.state_rolling)
            is PokerUIState.Settings    -> result = app.resources.getString(R.string.state_settings)
        }
        return result
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
                Persist.CASH                -> cash     = pokerDAO.cash
                Persist.COLOR_SUIT_NONE     -> setColor (SUIT_NONE, if (app.inDarkMode ()) pokerDAO.color_suit_none.second      else pokerDAO.color_suit_none.first)
                Persist.COLOR_SUIT_HEART    -> setColor (SUIT_NONE, if (app.inDarkMode ()) pokerDAO.color_suit_heart.second     else pokerDAO.color_suit_heart.first)
                Persist.COLOR_SUIT_DIAMOND  -> setColor (SUIT_NONE, if (app.inDarkMode ()) pokerDAO.color_suit_diamond.second   else pokerDAO.color_suit_diamond.first)
                Persist.COLOR_SUIT_CLUB     -> setColor (SUIT_NONE, if (app.inDarkMode ()) pokerDAO.color_suit_club.second      else pokerDAO.color_suit_club.first)
                Persist.COLOR_SUIT_SPADE    -> setColor (SUIT_NONE, if (app.inDarkMode ()) pokerDAO.color_suit_spade.second     else pokerDAO.color_suit_spade.first)
                Persist.LEVEL               -> level     = pokerDAO.level
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
                Persist.CASH                -> pokerDAO.cash     = cash
                Persist.COLOR_SUIT_NONE     -> if (app.inDarkMode ()) pokerDAO.color_suit_none.second    = app.color (Colors.Suit_None)     else pokerDAO.color_suit_none.first = app.color (Colors.Suit_None)
                Persist.COLOR_SUIT_HEART    -> if (app.inDarkMode ()) pokerDAO.color_suit_heart.second   = app.color (Colors.Suit_Heart)    else pokerDAO.color_suit_heart.first = app.color (Colors.Suit_Heart)
                Persist.COLOR_SUIT_DIAMOND  -> if (app.inDarkMode ()) pokerDAO.color_suit_diamond.second = app.color (Colors.Suit_Diamond)  else pokerDAO.color_suit_diamond.first = app.color (Colors.Suit_Diamond)
                Persist.COLOR_SUIT_CLUB     -> if (app.inDarkMode ()) pokerDAO.color_suit_club.second    = app.color (Colors.Suit_Club)     else pokerDAO.color_suit_club.first = app.color (Colors.Suit_Club)
                Persist.COLOR_SUIT_SPADE    -> if (app.inDarkMode ()) pokerDAO.color_suit_spade.second   = app.color (Colors.Suit_Spade)    else pokerDAO.color_suit_spade.first = app.color (Colors.Suit_Spade)
                Persist.LEVEL               -> pokerDAO.level   = level
            }
        }

        return pokerDAO
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Update the UI, or parts of it.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    fun updateBoard () {
        onUpdateBoard.value = !onUpdateBoard.value
    }

    fun updateBoardEdge () {
        onUpdateBoardEdge.value = !onUpdateBoardEdge.value
    }

    fun updateHandToBeat () {
        onUpdateHandToBeat.value = !onUpdateHandToBeat.value
    }

    fun updateInstructions () {
        onUpdateInstructions.value = !onUpdateInstructions.value
    }

    fun updatePlayer () {
        onUpdatePlayer.value = !onUpdatePlayer.value
    }

    fun updateGame () {
        updateBoard ()
        updateBoardEdge ()
        updateHandToBeat ()
        updateInstructions ()
        updatePlayer ()
    }

    /**
     * Populate the hand with dice.
     */
    fun populateHand () {
        for (pos in 0 until DICE_IN_HAND) {
            handToBeat.value [pos] = Die (randomRank(), randomSuit())
        }

        updateHandToBeat ()
    }

    /**
     * Score the hand.
     */
    fun scoreHand (hand: Array<Die>) {

    }

    fun getHandToBeatName (hand: Array<Die>): String {
        return app.getHandToBeatName (pokerScorer.computeHand (hand))
    }

    /**
     * The text for this square.
     */
    fun getSquareText (index: Int): String {
        // The squaere text.
        var result = NO_TEXT

        // The hand.
        val hand = game.getHand (index)

        // If it has at least one value grater than zero, we can score it.
        if (game.hasValue (hand)) {
            result = getHandToBeatName (hand)
        }

        return result
    }

    /**
     * Renew the edge squares text.
     */
    fun refreshEdgeSquareText () {
        for (pos in ZERO until NUM_SQUARES) {
            if (game.isEdgeSquare (pos)) {
                val dieText = getSquareText(pos)
                setDieText(pos, dieText)

                val oppPos = game.opposite(pos)
                setDieText(oppPos, dieText)

                // TODO Test
                if (!dieText.empty()) {
                    debug (dieText)
                }
            }
        }
    }

    /**
     * Set the die edge text.
     */
    fun setDieText (index: Int) {
        setDieText (index, getSquareText (index))
    }

    /**
     * Set the die edge text.
     */
    fun setDieText (index: Int, text: String) {
        game.getDie (index).name = text
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
            // Save button was pressed.
            is PokerEvent.StartEvent -> {
                startEvent (pokerEvent)
            }

            // Place your bets!
            is PokerEvent.BoardClickEvent -> {
                boardClickEvent (pokerEvent)
            }

            is PokerEvent.CancelEvent -> {
                cancelEvent (pokerEvent)
            }

            // Place your bets!
            is PokerEvent.EdgeClickEvent -> {
                edgeClickEvent (pokerEvent)
            }

            // Cash out!
            is PokerEvent.CashOutEvent -> {
                cashOutEvent (pokerEvent)
            }

            // Reset button pressed.
            is PokerEvent.ResetEvent -> {
                resetEvent (pokerEvent)
            }

            // Roll them bones.
            is PokerEvent.RollEvent -> {
                rollEvent (pokerEvent)
            }

            // Settings button pressed.
            is PokerEvent.SettingsEvent -> {
                settingsEvent (pokerEvent)
            }

            // Save button was pressed.
            is PokerEvent.SaveEvent -> {
                saveEvent (pokerEvent)
            }
        }
    }

    /**
     * Cancel!
     */
    fun cancelEvent (pokerEvent: PokerEvent.CancelEvent) {
        _uiState.value = PokerUIState.Start
        resetEvent (PokerEvent.ResetEvent)
    }

    /**
     * Cash out!
     */
    fun cashOutEvent (pokerEvent: PokerEvent.CashOutEvent) {

    }

    /**
     * Start the game.
     */
    fun startEvent (pokerEvent: PokerEvent.StartEvent) {
        // Set the rolls to 3
        rolls = ROLLS_MAX

        // Put some thing in my hand.
        populateHand ()

        // Populate the board with dice.
        game.populateBoard ()

        // Re-Calc Squares
        refreshEdgeSquareText ()

        // Update the board ad edge
        updateBoard()
        updateBoardEdge()

        _uiState.value = PokerUIState.Rolling
    }

    /**
     * Roll them bones.
     */
    fun rollEvent (pokerEvent: PokerEvent.RollEvent) {
        // Got rolls?
        if (rolls > SUIT_NONE_VAL) {
            // Decrement the rolls.
            rolls--

            // Populate the board with dice.
            game.populateBoard()

            // Re-Calc Squares
            refreshEdgeSquareText ()

            // Redraw the board and board edge.
            updateBoard ()
            updateBoardEdge()
        }
        else {
            // Game over.
            _uiState.value = PokerUIState.Start
        }
    }

    /**
     * Save Settings!
     */
    fun saveEvent (pokerEvent: PokerEvent.SaveEvent) {
        _uiState.value = PokerUIState.Start
        resetEvent (PokerEvent.ResetEvent)
    }

    /**
     * Reset!
     */
    fun resetEvent (pokerEvent: PokerEvent.ResetEvent) {
        reset ()
    }

    /**
     * Reset!
     */
    fun reset () {
        // Reset hand to beat.
        for (pos in 0 until DICE_IN_HAND) {
            handToBeat.value [pos] = Die (SUIT_NONE)
        }

        // Reset board.
        game.reset()

        // Reset rolls, bet and won.
        rolls   = 0
        bet     = 0
        won     = 0

        // Make text say Start
        _uiState.value = PokerUIState.Start

        updateGame ()
    }

    /**
     * Settings!
     */
    fun settingsEvent (pokerEvent: PokerEvent.SettingsEvent) {
        _uiState.value = PokerUIState.Settings
    }

    /**
     * They clicked on the board.
     */
    fun boardClickEvent (pokerEvent: PokerEvent.BoardClickEvent) {
        // Hold / Unhold die, if in rolling state.
        if (PokerUIState.Rolling == _uiState.value) {
            val die = game.getDie(pokerEvent.index)

            // toggle held color.
            die.held = !die.held

            // Put the die back.
            game.setDie(pokerEvent.index, die)
        }

        // Recompose the board
        onUpdateBoard.value = !onUpdateBoard.value
    }

    /**
     * They clicked on the board edge.
     */
    fun edgeClickEvent (pokerEvent: PokerEvent.EdgeClickEvent) {

        // What to do depends on state.
        if (PokerUIState.Start == _uiState.value) {
            edgeClickEventStart (pokerEvent)
        }

        // Recompose the board and board edge squares
        onUpdateBoard.value     = !onUpdateBoard.value
        onUpdateBoardEdge.value = !onUpdateBoardEdge.value
    }

    /**
     * They clicked on an edge square in the Start state.
     */
    fun edgeClickEventStart (pokerEvent: PokerEvent.EdgeClickEvent) {
        game.setBetSquares (pokerEvent.index)

        // Reset the bet.
        bet = game.computeBet ()

        updatePlayer()
        updateBoardEdge()
    }
}
