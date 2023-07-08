package com.pstorli.pokerdice.domain.model

/**
 * This class handlers various user events.
 */
sealed class PokerEvent {
    object AboutCloseEvent                      : PokerEvent()

    object AboutBtnPressedEvent                 : PokerEvent()

    data class BoardClickEvent(val index: Int)  : PokerEvent()

    data class EdgeClickEvent(val index: Int)   : PokerEvent()

    object CashOutEvent                         : PokerEvent()

    object RollEvent                            : PokerEvent()

    object SaveEvent                            : PokerEvent()

    object StartEvent                           : PokerEvent()

}