package com.pstorli.pokerdice.ui.viewmodel

/**
 * This class handlers various user events.
 */
sealed class PokerEvent {
    object BetEvent        : PokerEvent ()
    object CashOutEvent    : PokerEvent ()
    object HoldEvent       : PokerEvent ()
    object RollEvent       : PokerEvent ()
}