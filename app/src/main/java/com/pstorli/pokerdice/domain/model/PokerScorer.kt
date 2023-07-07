package com.pstorli.pokerdice.domain.model

import com.pstorli.pokerdice.util.Consts.DICE_IN_HAND
import com.pstorli.pokerdice.util.Consts.FIVE_OF_KIND
import com.pstorli.pokerdice.util.Consts.FLUSH
import com.pstorli.pokerdice.util.Consts.FOUR_OF_KIND
import com.pstorli.pokerdice.util.Consts.FULL_HOUSE
import com.pstorli.pokerdice.util.Consts.MAX_RANK
import com.pstorli.pokerdice.util.Consts.MAX_SUIT
import com.pstorli.pokerdice.util.Consts.NOTHING
import com.pstorli.pokerdice.util.Consts.NO_SUIT
import com.pstorli.pokerdice.util.Consts.ONE
import com.pstorli.pokerdice.util.Consts.ONE_PAIR
import com.pstorli.pokerdice.util.Consts.RANK_ONE
import com.pstorli.pokerdice.util.Consts.ROYAL_FLUSH
import com.pstorli.pokerdice.util.Consts.STRAIGHT
import com.pstorli.pokerdice.util.Consts.STRAIGHT_FLUSH
import com.pstorli.pokerdice.util.Consts.THREE_OF_KIND
import com.pstorli.pokerdice.util.Consts.TWO_PAIRS
import com.pstorli.pokerdice.util.Consts.ZERO

class PokerScorer () {

    // *********************************************************************************************
    // Vars
    // *********************************************************************************************
    var level = 0

    var royalFlush = false
    var flush = false
    var straight = false
    var fiveOfKind = false
    var fourOfKind = false
    var threeOfKind = false
    var pairCount = 0
    var rankOneCount = 0
    var rolledScore = 0
    var validStraight = true
    var zeroCount = 0

    /**
     * Return the highest die's rank.
     */
    fun highest (dice: Array<Die>): Int {
        return highestDie (dice).rank
    }

    /**
     * Return the highest die.
     */
    fun highestDie (dice: Array<Die>): Die
    {
        var die = dice[0]
        for (thisDie in dice) {
            if (thisDie.rank>die.rank) {
                die = thisDie
            }
        }
        return die
    }

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
        royalFlush = true
        flush = false
        straight = false
        fiveOfKind = false
        fourOfKind = false
        threeOfKind = false
        pairCount = 0
        rankOneCount = 0
        rolledScore = 0
        zeroCount = 0

        // First look for a flush.
        val suitCounts = getSuitCount(dice)
        for (element in suitCounts) {
            if (element >= DICE_IN_HAND) {
                flush = true
                break
            }
        }

        // Count how many of each dice.
        val rankCounts = getRankCount(dice)

        // Look for scores.
        // Search by how many things we have.
        var validStraight = true
        for (pos in ONE until rankCounts.size) {
            val rankCount = rankCounts[pos]
            when (rankCount) {
                1 -> {              // A straight?
                    // A straight?
                    if (validStraight) {
                        straight = true
                    }
                    rankOneCount++
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
                    // Possibly zero
                    zeroCount++

                    // Straights allow for no gaps.
                    if (zeroCount>ONE) {
                        straight = false
                        validStraight = false
                    }
                }
            }
        }

        // Straight jacket check.
        if (rankOneCount < DICE_IN_HAND) {
            // No straight, no royal flush.
            royalFlush = false
            straight = false
        }
        else if (ONE == rankCounts[RANK_ONE]) {
            // No royal flush.
            royalFlush = false
        }

        // Any score?
        rolledScore = if (straight && royalFlush) {
            // A five of a kind.
            ROYAL_FLUSH
        }

        // A straight flush?
        else if (straight && flush) {
            STRAIGHT_FLUSH
        }

        // Five of a kind.
        else if (fiveOfKind) {
            FIVE_OF_KIND
        }

        // Four of a kind.
        else if (fourOfKind) {
            FOUR_OF_KIND
        }

        // Full house.
        else if (threeOfKind && pairCount > 0) {
            FULL_HOUSE
        }

        // Flush.
        else if (flush) {
            FLUSH
        }

        // Straight.
        else if (straight) {
            STRAIGHT
        }

        // Three of a kind.
        else if (threeOfKind) {
            THREE_OF_KIND
        }

        // Two pairs.
        else if (pairCount > 1) {
            TWO_PAIRS
        }

        // One pair.
        else if (pairCount > 0) {
            ONE_PAIR
        }

        else {
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
        for (pos in dice.indices) {
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
     *
     * @return an array with the rank counts.
     *
     * 0 = no rank's count
     * 1 = one's count
     * 2 = two's count
     * 3 = three's count
     * 4 = four's count
     * 5 = five's count
     * 6 = sixes count
     */
    fun getRankCount (dice: Array<Die>): IntArray {
        // Create and initialize the rank array.
        val ranks = IntArray(MAX_RANK+1) { ZERO }

        // Go through the dice.
        for (pos in dice.indices) {

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