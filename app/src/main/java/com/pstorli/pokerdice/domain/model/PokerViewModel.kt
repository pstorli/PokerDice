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
import com.pstorli.pokerdice.util.Consts.BOARD_FIRST
import com.pstorli.pokerdice.util.Consts.BOARD_LAST

import com.pstorli.pokerdice.util.Consts.BOARD_SIZE
import com.pstorli.pokerdice.util.Consts.CASH_INITIAL
import com.pstorli.pokerdice.util.Consts.DICE_ZERO
import com.pstorli.pokerdice.util.Consts.GAME_SAVED
import com.pstorli.pokerdice.util.Consts.HAND_TO_BEAT_SIZE
import com.pstorli.pokerdice.util.Consts.ROLLS_MAX
import com.pstorli.pokerdice.util.Consts.col
import com.pstorli.pokerdice.util.Consts.index
import com.pstorli.pokerdice.util.Consts.row
import com.pstorli.pokerdice.util.Persist
import com.pstorli.pokerdice.util.Prefs

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

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
    var cash                    by mutableStateOf<Int>(0)

    // How many rolls do we have left.
    var rolls                   by mutableStateOf<Int>(0)

    // What hand do we need to beat? Both? ;) Pete
    val handToBeat              = mutableStateOf(Array<Die>(HAND_TO_BEAT_SIZE){Die()})

    // What level are we on?
    var level                   by mutableStateOf<Int>(0)

    // How much have we won?
    var won                     by mutableStateOf<Int>(0)

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Vars
    // /////////////////////////////////////////////////////////////////////////////////////////////

    // Preferences
    var prefs: Prefs

    // The game board, 7X7 AS A list of 49 Dice items
    var board = Array(BOARD_SIZE*BOARD_SIZE){Die()}

    // The poker repo is used to save game state information.
    var pokerRepo: PokerRepo

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Game State
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * TODO: Comsider using an enum instad of a seled class.
     */
    sealed class PokerUIState {
        object Loading : PokerUIState()

        object Start : PokerUIState()

        object Rolling : PokerUIState()

        class Error (val message: String) : PokerUIState()
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
            is PokerUIState.Error   -> result = app.resources.getString(R.string.state_error)
            is PokerUIState.Start   -> result = app.resources.getString(R.string.state_start)
            is PokerUIState.Loading -> result = app.resources.getString(R.string.state_loading)
            is PokerUIState.Rolling -> result = app.resources.getString(R.string.state_rolling)
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
                Persist.BET                 -> bet      = pokerDAO.bet
                Persist.BOARD               -> board    = pokerDAO.board
                Persist.CASH                -> cash     = pokerDAO.cash
                Persist.COLOR_DICE0         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice0.second else pokerDAO.colorDice0.first)
                Persist.COLOR_DICE1         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice1.second else pokerDAO.colorDice1.first)
                Persist.COLOR_DICE2         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice2.second else pokerDAO.colorDice2.first)
                Persist.COLOR_DICE3         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice3.second else pokerDAO.colorDice3.first)
                Persist.COLOR_DICE4         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice4.second else pokerDAO.colorDice4.first)
                Persist.COLOR_DICE5         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice5.second else pokerDAO.colorDice5.first)
                Persist.COLOR_DICE6         -> setColor (DICE_ZERO, if (app.inDarkMode ()) pokerDAO.colorDice6.second else pokerDAO.colorDice6.first)
                Persist.LEVEL               -> level     = pokerDAO.level
                Persist.ROLLS               -> rolls     = pokerDAO.rolls
                Persist.WON                 -> won       = pokerDAO.won
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
                Persist.BET                 -> pokerDAO.bet      = bet
                Persist.BOARD               -> pokerDAO.board    = board
                Persist.CASH                -> pokerDAO.cash     = cash
                Persist.COLOR_DICE0         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice0) else pokerDAO.colorDice0.first = app.color (Colors.Dice0)
                Persist.COLOR_DICE1         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice1) else pokerDAO.colorDice0.first = app.color (Colors.Dice1)
                Persist.COLOR_DICE2         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice2) else pokerDAO.colorDice0.first = app.color (Colors.Dice2)
                Persist.COLOR_DICE3         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice3) else pokerDAO.colorDice0.first = app.color (Colors.Dice3)
                Persist.COLOR_DICE4         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice4) else pokerDAO.colorDice0.first = app.color (Colors.Dice5)
                Persist.COLOR_DICE5         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice5) else pokerDAO.colorDice0.first = app.color (Colors.Dice5)
                Persist.COLOR_DICE6         -> if (app.inDarkMode ()) pokerDAO.colorDice0.second = app.color (Colors.Dice6) else pokerDAO.colorDice0.first = app.color (Colors.Dice6)
                Persist.LEVEL               -> pokerDAO.level   = level
                Persist.ROLLS               -> pokerDAO.rolls   = rolls
                Persist.WON                 -> pokerDAO.won     = won
            }
        }

        return pokerDAO
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Set the bet / not-bet squares.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Make this square and opposite square have their bet index set.
     */
    fun setBetSquares (index: Int) {
        // What row/col did they click?
        val row     = row(index)
        val col     = col(index)
        val dice    = board [index]
        val select  = !dice.selected

        // ****************************************
        // A corner?
        // ****************************************

        // Lower right or                                Upper left?
        if ((BOARD_LAST == row && BOARD_LAST == col) || (BOARD_FIRST == row && BOARD_FIRST == col)) {
            // Set upper left and lower right.
            board [index(BOARD_LAST,BOARD_LAST)].selected   = select
            board [index(BOARD_FIRST,BOARD_FIRST)].selected = select
        }

        // Lower left or                                 Upper right?
        else if ((BOARD_LAST == row && BOARD_FIRST == col) || (BOARD_FIRST == row && BOARD_LAST == col)) {
            // Set lower left and upper right.
            board [index(BOARD_LAST,BOARD_FIRST)].selected  = select
            board [index(BOARD_FIRST,BOARD_LAST)].selected  = select
        }

        // ****************************************
        // Straight shot vertically?
        // ****************************************
        else if (BOARD_LAST == row || BOARD_FIRST == row) {
            // Set Bottom and top.
            board [index(BOARD_LAST,col)].selected          = select
            board [index(BOARD_FIRST,col)].selected         = select
        }

        // ****************************************
        // Straight shot horizontally?
        // ****************************************
        else if (BOARD_LAST == col || BOARD_FIRST == col) {
            // Set Left and right.
            board [index(row, BOARD_LAST)].selected         = select
            board [index(row, BOARD_FIRST)].selected        = select
        }

        // Something else.
        else {
            board [index].selected = !board [index].selected
        }
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

    fun updateGameUI () {
        updateBoard ()
        updateBoardEdge ()
        updateHandToBeat ()
        updateInstructions ()
        updatePlayer ()
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Populate the board.
    // /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Populate the board with dice.
     */
    fun populateBoard () {
        // Get some dice.
        for (row in BOARD_FIRST+1 until BOARD_LAST) {
            for (col in BOARD_FIRST + 1 until BOARD_LAST) {
                val index = index(row, col)
                val die = board.get(index)

                // If square is not held
                if (!die.held) {
                    board[index] = Die (Random.nextInt(BOARD_LAST))
                }
            }
        }

        updateBoard ()
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
            is PokerEvent.BoardClickEvent -> {
                boardClickEvent (pokerEvent)
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
        }
    }

    /**
     * Cash out!
     */
    fun cashOutEvent (pokerEvent: PokerEvent.CashOutEvent) {

    }

    /**
     * Roll them bones.
     */
    fun rollEvent (pokerEvent: PokerEvent.RollEvent) {
        // First time?
        if (PokerUIState.Start == _uiState.value) {
            // Set the rolls to 3
            rolls = ROLLS_MAX
        }

        // Populate the board with dice.
        populateBoard ()

        _uiState.value = PokerUIState.Rolling
    }

    /**
     * They clicked on the board.
     */
    fun boardClickEvent (pokerEvent: PokerEvent.BoardClickEvent) {
        // Hold / Unhold die, if in rolling state.
        if (PokerUIState.Rolling == _uiState.value) {
            board [pokerEvent.index].held = !board [pokerEvent.index].held
        }

        // Recompose the board
        onUpdateBoard.value = !onUpdateBoard.value
    }

    /**
     * They clicked on the board edge.
     */
    fun edgeClickEvent (pokerEvent: PokerEvent.EdgeClickEvent) {

        // What to do depends on state.
        when (_uiState.value) {
            is PokerUIState.Error   -> app.resources.getString(R.string.state_error)
            is PokerUIState.Loading -> app.resources.getString(R.string.state_loading)
            is PokerUIState.Start   -> edgeClickEventStart (pokerEvent)
            is PokerUIState.Rolling -> "Noop" // Edge clicking not allowed during rolling.
        }

        // Recompose the board and board edge squares
        onUpdateBoard.value     = !onUpdateBoard.value
        onUpdateBoardEdge.value = !onUpdateBoardEdge.value
    }

    /**
     * They clicked on an edge square in the Start state.
     */
    fun edgeClickEventStart (pokerEvent: PokerEvent.EdgeClickEvent) {
        setBetSquares (pokerEvent.index)
        updateBoardEdge()
    }
}
