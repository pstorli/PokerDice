package com.pstorli.pokerdice

import com.pstorli.pokerdice.domain.model.Die
import com.pstorli.pokerdice.domain.model.PokerScorer
import com.pstorli.pokerdice.util.Consts.FIVE_OF_KIND
import com.pstorli.pokerdice.util.Consts.FLUSH
import com.pstorli.pokerdice.util.Consts.FOUR_OF_KIND
import com.pstorli.pokerdice.util.Consts.FULL_HOUSE
import com.pstorli.pokerdice.util.Consts.ONE_PAIR
import com.pstorli.pokerdice.util.Consts.ROYAL_FLUSH
import com.pstorli.pokerdice.util.Consts.STRAIGHT
import com.pstorli.pokerdice.util.Consts.STRAIGHT_FLUSH
import com.pstorli.pokerdice.util.Consts.SUIT_CLUB
import com.pstorli.pokerdice.util.Consts.SUIT_DIAMOND
import com.pstorli.pokerdice.util.Consts.SUIT_HEART
import com.pstorli.pokerdice.util.Consts.SUIT_SPADE
import com.pstorli.pokerdice.util.Consts.THREE_OF_KIND
import com.pstorli.pokerdice.util.Consts.TWO_PAIRS
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PokerTest {
    @Test
    fun poker () {
        val pokerScorer    = PokerScorer ()
        var allTestsPassed = true

        // Royal flush
        var hand = arrayOf<Die> (
            Die (2,SUIT_HEART),
            Die (3,SUIT_HEART),
            Die (4,SUIT_HEART),
            Die (5,SUIT_HEART),
            Die (6,SUIT_HEART))

        var score = pokerScorer.scoreHand (hand)
        if (ROYAL_FLUSH != score) {
            allTestsPassed = false
            "Royal flush fail: $score".debug()
        }

        // Straight flush
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (2,SUIT_HEART),
            Die (3,SUIT_HEART),
            Die (4,SUIT_HEART),
            Die (5,SUIT_HEART))

        score = pokerScorer.scoreHand (hand)
        if (STRAIGHT_FLUSH != score) {
            allTestsPassed = false
            "Straight flush fail: $score".debug()
        }

        // Five of a kind
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (1,SUIT_CLUB),
            Die (1,SUIT_SPADE),
            Die (1,SUIT_DIAMOND),
            Die (1,SUIT_HEART))

        score = pokerScorer.scoreHand (hand)
        if (FIVE_OF_KIND != score) {
            allTestsPassed = false
            "Five of a kind fail: $score".debug()
        }

        // Four of a kind
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (1,SUIT_CLUB),
            Die (2,SUIT_SPADE),
            Die (1,SUIT_DIAMOND),
            Die (1,SUIT_HEART))

        score = pokerScorer.scoreHand (hand)
        if (FOUR_OF_KIND != score) {
            allTestsPassed = false
            "Four of a kind fail: $score".debug()
        }

        // Full house
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (1,SUIT_CLUB),
            Die (2,SUIT_SPADE),
            Die (2,SUIT_DIAMOND),
            Die (2,SUIT_HEART))

        score = pokerScorer.scoreHand (hand)
        if (FULL_HOUSE != score) {
            allTestsPassed = false
            "Full house fail: $score".debug()
        }

        // Flush
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (1,SUIT_HEART),
            Die (2,SUIT_HEART),
            Die (3,SUIT_HEART),
            Die (6,SUIT_HEART))

        score = pokerScorer.scoreHand (hand)
        if (FLUSH != score) {
            allTestsPassed = false
            "Flush fail: $score".debug()
        }

        // Straight
        hand = arrayOf<Die> (
            Die (1,SUIT_HEART),
            Die (2,SUIT_CLUB),
            Die (3,SUIT_HEART),
            Die (4,SUIT_SPADE),
            Die (5,SUIT_DIAMOND))

        score = pokerScorer.scoreHand (hand)
        if (STRAIGHT != score) {
            allTestsPassed = false
            "Straight fail: $score".debug()
        }

        // Three of a kind
        hand = arrayOf<Die> (
            Die (3,SUIT_HEART),
            Die (2,SUIT_CLUB),
            Die (3,SUIT_HEART),
            Die (3,SUIT_SPADE),
            Die (5,SUIT_DIAMOND))

        score = pokerScorer.scoreHand (hand)
        if (THREE_OF_KIND != score) {
            allTestsPassed = false
            "Three of a kind fail: $score".debug()
        }

        // Two pair
        hand = arrayOf<Die> (
            Die (3,SUIT_HEART),
            Die (2,SUIT_CLUB),
            Die (3,SUIT_HEART),
            Die (2,SUIT_SPADE),
            Die (5,SUIT_DIAMOND))

        score = pokerScorer.scoreHand (hand)
        if (TWO_PAIRS != score) {
            allTestsPassed = false
            "Two pair fail: $score".debug()
        }

        // One pair
        hand = arrayOf<Die> (
            Die (3,SUIT_HEART),
            Die (6,SUIT_CLUB),
            Die (3,SUIT_HEART),
            Die (2,SUIT_SPADE),
            Die (5,SUIT_DIAMOND))

        score = pokerScorer.scoreHand (hand)
        if (ONE_PAIR != score) {
            allTestsPassed = false
            "One pair fail: $score".debug()
        }

        // All Good?
        if (allTestsPassed) {
            "All scoring tests have passed!".debug()
        }
        else {
            "One or more scoring tests have failed!".debug()
        }
    }
}