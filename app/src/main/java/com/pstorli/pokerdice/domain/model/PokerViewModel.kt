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
import com.pstorli.pokerdice.util.Consts

import com.pstorli.pokerdice.util.Consts.CASH_INITIAL
import com.pstorli.pokerdice.util.Consts.SUIT_NONE
import com.pstorli.pokerdice.util.Consts.GAME_SAVED
import com.pstorli.pokerdice.util.Consts.DICE_IN_HAND
import com.pstorli.pokerdice.util.Consts.NO_TEXT
import com.pstorli.pokerdice.util.Consts.NUM_SQUARES
import com.pstorli.pokerdice.util.Consts.ONE
import com.pstorli.pokerdice.util.Consts.ROLLS_MAX
import com.pstorli.pokerdice.util.Consts.SQUARE_BET_COST
import com.pstorli.pokerdice.util.Consts.SQUARE_FIRST
import com.pstorli.pokerdice.util.Consts.WIN_LEVEL_MOD
import com.pstorli.pokerdice.util.Consts.ZERO
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

    // How much cash, rolls and bets have we?
    var bet                     by mutableStateOf<Int>(0)
    var cash                    by mutableStateOf<Int>(CASH_INITIAL)

    // How many rolls do we have left.
    var rolls                   by mutableStateOf<Int>(0)

    // What hand do we need to beat? Both? ;) Pete
    val handToBeat              = mutableStateOf(Array<Die>(DICE_IN_HAND){Die()})

    // What level are we on?
    var level                   by mutableStateOf<Int>(ONE)

    // Set this to show snackbar text.
    var snackBarText            by mutableStateOf<String> (NO_TEXT)

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

    // Don't offer money unless they get out of cash message.
    var outaCash = false

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Game State
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * TODO: Comsider using an enum instad of a seled class.
     */
    sealed class PokerUIState {
        object About                        : PokerUIState()

        object Loading                      : PokerUIState()

        object Start                        : PokerUIState()

        object Rolling                      : PokerUIState()

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

        reset ()
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
     *
     */
    fun scoreHandToBeat (): Int {
        return pokerScorer.scoreHand (handToBeat.value)
    }

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
            is PokerUIState.About    -> result = app.resources.getString(R.string.state_about)
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

    fun getHandName (hand: Array<Die>): String {
        return getHandName (pokerScorer.scoreHand (hand))
    }

    fun getHandName (score: Int): String {
        return app.getHandName (score)
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
            result = getHandName (hand)
        }

        return result
    }

    /**
     * Renew the edge squares text.
     */
    fun refreshEdgeSquareText () {
        won = 0
        for (pos in ZERO until NUM_SQUARES) {
            if (game.isEdgeSquare(pos)) {
                // Show score, not text if bottom or right side.
                val rowCol = game.rowCol(pos)

                // The hand.
                val hand = game.getHand(pos)

                // If it has at least one value grater than zero, we can score it.
                var dieText = getHandName(hand)

                // The hand score.
                val handScore = pokerScorer.scoreHand(hand)

                // The hand's value.
                if (game.hasValue(hand)) {
                    val handToBeatScore = scoreHandToBeat ()

                    // They win if that square was selected and they beat hand to beat.
                    if (game.board[pos].selected && handScore > handToBeatScore) {
                        // Only add winnings if square is top or left, and not upper right
                        if (!game.isCornerUR(pos) && (game.isEdgeSquareLeft(pos) || game.isEdgeSquareTop(pos))) {
                            won = won + handScore
                        }
                    }
                }

                // Should we show hand value or hand name?
                if (SQUARE_FIRST != rowCol.second && (game.isEdgeSquareRight(pos) || game.isEdgeSquareBottom(pos))) {
                    dieText = handScore.toString()
                }

                // Set the text now.
                setDieText (pos, dieText)
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
        val die = game.getDie (index)
        die.name = text
        game.setDie(index, die)
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Handle PokerEvents
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * User did something.
     */
    fun onEvent (pokerEvent: PokerEvent) {
        when (pokerEvent) {
            // Give them some more cash.
            PokerEvent.AddCashEvent -> {
                addCash ()
            }

            // Save button was pressed.
            is PokerEvent.StartEvent -> {
                startEvent (pokerEvent)
            }

            // Place your bets!
            is PokerEvent.BoardClickEvent -> {
                boardClickEvent (pokerEvent)
            }

            // About close button pressed.
            is PokerEvent.AboutCloseEvent -> {
                this.aboutCloseEvent(pokerEvent)
            }

            // About button pressed.
            is PokerEvent.AboutBtnPressedEvent -> {
                this.aboutBtnPressedEvent(pokerEvent)
            }

            // Place your bets!
            is PokerEvent.EdgeClickEvent -> {
                edgeClickEvent (pokerEvent)
            }

            // Cash out!
            is PokerEvent.CashOutEvent -> {
                cashOutEvent (pokerEvent)
            }

            // Roll them bones.
            is PokerEvent.RollEvent -> {
                rollEvent (pokerEvent)
            }

            // Save button was pressed.
            is PokerEvent.SaveEvent -> {
                saveEvent (pokerEvent)
            }
        }
    }

    /**
     * Cash out!
     */
    fun cashOutEvent (pokerEvent: PokerEvent.CashOutEvent) {
        cashOut ()
    }

    /**
     * Add some cash.
     */
    fun addCash () {
        cash += CASH_INITIAL

        outaCash = false

        updatePlayer()
    }

    fun cashOut () {
        // Update cash
        cash = cash - bet + won

        // Increase the level
        level++

        // Show a snack bar about winning the level
        snackBarText = if (freeGameOffer ()) app.resources.getString(R.string.won_level_prize, level.toString()) else NO_TEXT

        // Save the game.
        saveGame ()

        reset ()
    }

    /**
     * Show free game offer?
     */
    fun freeGameOffer (): Boolean {
        return level>ZERO && ZERO == (level % WIN_LEVEL_MOD)
    }

    /**
     * About!
     */
    fun aboutBtnPressedEvent (pokerEvent: PokerEvent.AboutBtnPressedEvent) {
        _uiState.value = PokerUIState.About
    }

    /**
     * About Close
     */
    fun aboutCloseEvent (pokerEvent: PokerEvent.AboutCloseEvent) {
        _uiState.value = PokerUIState.Start
        reset ()
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
        updatePlayer()

        _uiState.value = PokerUIState.Rolling
    }

    /**
     * Roll them bones.
     */
    fun rollEvent (pokerEvent: PokerEvent.RollEvent) {
        // Decrement the rolls.
        rolls--

        // Got rolls?
        if (rolls > ZERO) {

            // Populate the board with dice.
            game.populateBoard()

            // Re-Calc Squares
            refreshEdgeSquareText ()

            // Redraw the board and board edge.
            updateBoard ()
            updateBoardEdge()
            updatePlayer()
        }
        else {
            // Game over.
            cashOut ()
        }
    }

    /**
     * Save Settings!
     */
    fun saveGame () {
        // Save game.
        viewModelScope.launch {
            pokerRepo.saveDAO(fromModel ())
        }

        // The game has been saved.
        prefs.putBool(GAME_SAVED, true)

        _uiState.value = PokerUIState.Start
    }

    /**
     * Save Settings!
     */
    fun saveEvent (pokerEvent: PokerEvent.SaveEvent) {
        saveGame ()
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
        rolls   = ROLLS_MAX
        bet     = 0
        won     = 0

        // Make text say Start
        _uiState.value = PokerUIState.Start

        updateGame ()
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

            Consts.playSound(R.raw.hold, getApplication())

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
        // Each click costs 2 * SQUARE_BET_COST (10)
        val dice = game.board [pokerEvent.index]

        // Let them de-select or proceed if they have enough money
        val betsCost = bet + 2 * SQUARE_BET_COST
        if (dice.selected || cash >= betsCost) {
            // Set the squares to bet on.
            game.setBetSquares (pokerEvent.index)
        }
        else if (cash < betsCost) {
            // Out of cash!
            snackBarText = app.resources .getString(R.string.outta_cash)
            outaCash     = true
        }

        // Reset the bet.
        bet = game.computeBet()

        updatePlayer()
        updateBoardEdge()
    }
}
