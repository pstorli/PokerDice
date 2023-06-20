package com.pstorli.pokerdice.domain.model

/**
 * This class handlers various user events.
 */
sealed class PokerEvent {
    data class BoardClickEvent(val index: Int)  : PokerEvent()

    data class EdgeClickEvent(val index: Int)   : PokerEvent()

    object CancelEvent                          : PokerEvent()

    object CashOutEvent                         : PokerEvent()

    object ResetEvent                           : PokerEvent()

    object RollEvent                            : PokerEvent()

    object SaveEvent                            : PokerEvent()

    object StartEvent                           : PokerEvent()

    object SettingsEvent                        : PokerEvent()
}