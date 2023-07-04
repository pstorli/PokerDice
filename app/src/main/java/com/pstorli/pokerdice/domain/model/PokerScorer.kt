package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.util.Consts.FLUSH
import com.pstorli.pokerdice.util.Consts.FOUR_OF_KIND
import com.pstorli.pokerdice.util.Consts.FULL_HOUSE
import com.pstorli.pokerdice.util.Consts.MAX_RANK
import com.pstorli.pokerdice.util.Consts.MAX_SUIT
import com.pstorli.pokerdice.util.Consts.NOTHING
import com.pstorli.pokerdice.util.Consts.ZERO
import com.pstorli.pokerdice.util.Consts.NO_RANK
import com.pstorli.pokerdice.util.Consts.NO_SUIT
import com.pstorli.pokerdice.util.Consts.ONE_PAIR
import com.pstorli.pokerdice.util.Consts.ROYAL_FLUSH
import com.pstorli.pokerdice.util.Consts.STRAIGHT
import com.pstorli.pokerdice.util.Consts.STRAIGHT_FLUSH
import com.pstorli.pokerdice.util.Consts.THREE_OF_KIND
import com.pstorli.pokerdice.util.Consts.TWO_PAIRS

class PokerScorer () {

    // *********************************************************************************************
    // Vars
    // *********************************************************************************************
    var level = 0

    var flush = false
    var straight = false
    var fiveOfKind = false
    var fourOfKind = false
    var threeOfKind = false
    var pairCount = 0
    var SUIT_NONE_COUNT = 0
    var rolledScore = 0

    var validStraight = true

    /**
     * Compute the value of this hand.
     *
     * @param dice
     *
     * @return value of hand.
     */
    fun scoreHand (dice: Array<Die>): Int
    {
        // Do we have a flush?
        flush = false
        straight = false
        fiveOfKind = false
        fourOfKind = false
        threeOfKind = false
        pairCount = 0
        SUIT_NONE_COUNT = 0
        rolledScore = 0

        // First look for a flush.
        val suitCounts = getSuitCount(dice)
        for (element in suitCounts) {
            if (element >= MAX_SUIT) {
                flush = true
                break
            }
        }

        // Count how many of each dice.
        val rankCounts = getRankCount(dice, MAX_RANK)

        // Look for scores.
        // Search by how many things we have.
        validStraight = true
        for (pos in rankCounts.indices) {
            val rankCount = rankCounts[pos]
            when (rankCount) {
                1 ->                // A straight?
                    if (validStraight) {
                        straight = true
                    }

                2 -> {
                    // No straight, but pair
                    straight = false
                    validStraight = false
                    pairCount++
                }

                3 -> {
                    // No straight, but three of kind.
                    straight = false
                    validStraight = false
                    threeOfKind = true
                }

                4 -> {
                    // No straight, but four of kind.
                    straight = false
                    validStraight = false
                    fourOfKind = true
                }

                5 -> {
                    // No straight, but five of kind.
                    straight = false
                    validStraight = false
                    fiveOfKind = true
                }

                else -> {
                    SUIT_NONE_COUNT++
                    if (SUIT_NONE_COUNT > 1 || pos > 0 && pos < MAX_RANK) {
                        // No straight, no nothing.
                        straight = false
                        validStraight = false
                    }
                }
            }
        }

        // Any score?
        rolledScore = if (fiveOfKind) {
            // A five of a kind.
            ROYAL_FLUSH
        } else if (straight && flush) {
            // A straight flush?
            STRAIGHT_FLUSH
        } else if (fourOfKind) {
            // Four of a kind.
            FOUR_OF_KIND
        } else if (threeOfKind && pairCount > 0) {
            // Full house.
            FULL_HOUSE
        } else if (flush) {
            // Flush.
            FLUSH
        } else if (straight) {
            // Straight.
            STRAIGHT
        } else if (threeOfKind) {
            // Three of a kind.
            THREE_OF_KIND
        } else if (pairCount > 1) {
            // Two pairs.
            TWO_PAIRS
        } else if (pairCount > 0) {
            // One pair.
            ONE_PAIR
        } else {
            // Nothing :(
            NOTHING
        }
        return rolledScore
    }

    /**
     * Determine the number of suits in the given hand.
     *
     * @param dice
     *
     * @return an array with the suit counts.
     *
     * Array Index 0 = no suit's count
     * Array Index 1 = heart's count
     * Array Index 2 = diamond's count
     * Array Index 3 = club's count
     * Array Index 4 = spade's count
     */
    fun getSuitCount(dice: Array<Die>): IntArray {
        // Create and initialize the suit array.
        // var arr = IntArray(size){0}
        val suits  = IntArray (MAX_SUIT + 1) {NO_SUIT}

        // Go through the dice.
        for (pos in ZERO until dice.size) {
            // Get the suit and rank of this dice.
            val card: Die = dice[pos]

            // Get the suit.
            val suit: Int = card.suit

            // Increment the suit count.
            if (suit > ZERO) {
                suits[suit] = suits[suit] + 1
            }
        }
        return suits
    }

    /**
     * Determine the number of ranks in the given hand.
     *
     * @paarm start
     * @param dice
     * @param maxRank
     *
     * @return an array with the rank counts.
     *
     * 0 = no rank's count
     * 1 = one's count
     * 2 = two's count
     * 3 = three's count
     * 4 = four's count
     * 5 = five's count
     * 6 = sixes count    O_RANK
     */
    fun getRankCount (dice: Array<Die>, maxRank: Int): IntArray {
        // Create and initialize the rank array.
        val ranks = IntArray(maxRank + 1) { NO_RANK }

        // Go through the dice.
        for (pos in ZERO until dice.size) {

            // Get the suit and rank of this dice.
            val card: Die = dice[pos]

            // Get the rank.
            val rank: Int = card.rank

            // Increment the rank count, suit must be greater than  0
            if (card.suit > NO_SUIT) {
                ranks[rank] = ranks[rank] + 1
            }
        }
        return ranks
    }

    /**
     * What is the highest value in this hand?
     */
    fun computeRank (dice: Array<Die>): Int {
        var result = NOTHING
        for (pos in NOTHING .. dice.size) {
            if (dice[pos].rank > result) {
                result = dice[pos].rank
            }
        }
        return result
    }
}