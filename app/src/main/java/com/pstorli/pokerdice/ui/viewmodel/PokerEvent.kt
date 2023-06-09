package com.pstorli.pokerdice.ui.viewmodel

/**
 * This class handlers various user events.
 */
sealed class PokerEvent {
    class BoardClickEvent(index: Int)   : PokerEvent()

    object CashOutEvent                 : PokerEvent ()

    object RollEvent                    : PokerEvent ()
}