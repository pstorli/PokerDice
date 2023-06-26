package com.pstorli.pokerdice.domain.model


class PokerScorer () {
    // How many cards in a hand?
    val MAX_CARDS = 5

    // None and the four suits.
    val SUIT_NONE       = 0 //	Array Index 0 = no suit's count
    val SUIT_HEART      = 1 //	Array Index 1 = heart's count
    val SUIT_DIAMOND    = 2 // Array Index 2 = diamond's count
    val SUIT_CLUB       = 3 // Array Index 3 = club's count
    val SUIT_SPADE      = 4 // Array Index 4 = spade's count
    
    val NO_RANK = 0
    val NO_SUIT = 0

    val MIN_SUIT = 1
    val MIN_RANK = 1
    val MAX_SUIT = 4
    val MAX_RANK = 13

    // The score data.
    val FIVE_OF_KIND = 9
    val STRAIGHT_FLUSH = 8
    val FOUR_OF_KIND = 7
    val FULL_HOUSE = 6
    val FLUSH = 5
    val STRAIGHT = 4
    val THREE_OF_KIND = 3
    val TWO_PAIRS = 2
    val ONE_PAIR = 1
    val NOTHING = 0

    var level = 0

    var flush = false
    var straight = false
    var fiveOfKind = false
    var fourOfKind = false
    var threeOfKind = false
    var pairCount = 0
    var SUIT_NONECount = 0
    var rolledScore = 0

    var validStraight = true


    /**
     * Compute the value of this hand.
     *
     * @param start
     * @param dice
     * @maxRank
     *
     * @return
     */
    fun computeHand (start: Int, dice: List<Die>): Int
    {
        // Do we have a flush?
        flush = false
        straight = false
        fiveOfKind = false
        fourOfKind = false
        threeOfKind = false
        pairCount = 0
        SUIT_NONECount = 0
        rolledScore = 0

        // First look for a flush.
        val suitCounts = getSuitCount(start, dice)
        for (element in suitCounts) {
            if (element >= 5) {
                flush = true
                break
            }
        }

        // Count how many of each dice.
        val rankCounts = getRankCount(start, dice, MAX_RANK)

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
                    SUIT_NONECount++
                    if (SUIT_NONECount > 1 || pos > 0 && pos < 5) {
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
            FIVE_OF_KIND
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
    fun getSuitCount(start: Int, dice: List<Die>): List<Int> {
        // Create and initialize the suit array.
        val suits: MutableList<Int> = ArrayList(MAX_SUIT + 1)
        for (suit in NO_SUIT..MAX_SUIT) {
            suits.add(NO_SUIT)
        }

        // Go through the dice.
        var pos = start
        while (pos < dice.size && pos < start + 5) {

            // Get the suit and rank of this dice.
            val card: Die = dice[pos]

            // Get the suit.
            val suit: Int = card.suit

            // Increment the suit count.
            if (suit > 0) {
                suits[suit] = suits[suit] + 1
            }
            pos++
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
    fun getRankCount(start: Int, dice: List<Die>, maxRank: Int): List<Int> {
        // Create and initialize the rank array.
        val ranks: MutableList<Int> = ArrayList(maxRank + 1)
        for (rank in NO_RANK..maxRank) {
            ranks.add(NO_RANK)
        }

        // Go through the dice.
        var pos = start
        while (pos < dice.size && pos < start + 5) {

            // Get the suit and rank of this dice.
            val card: Die = dice[pos]

            // Get the rank.
            val rank: Int = card.rank

            // Increment the rank count, suit must be greater than  0
            if (card.suit > NO_SUIT) {
                ranks[rank] = ranks[rank] + 1
            }
            pos++
        }
        return ranks
    }
}