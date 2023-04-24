package com.sd.droppoker.scorers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.sd.core.Helper;
import com.sd.core.data.Card;
import com.sd.core.data.Scorer;
import com.sd.core.util.Num;
import com.sd.droppoker.R;

public class PokerScorer implements Scorer
{
	// How many cards in a hand?
	public static final int         MAX_CARDS                   = 5;
	
	// None and the four suits.
	public static final int 		SUIT_NONE 					= 0; //	Array Index 0 = no suit's count
	public static final int  		SUIT_HEART 					= 1; //	Array Index 1 = heart's count
	public static final int 		SUIT_DIAMOND 				= 2; // Array Index 2 = diamond's count
	public static final int 		SUIT_CLUB 					= 3; // Array Index 3 = club's count
	public static final int 		SUIT_SPADE 					= 4; // Array Index 4 = spade's count 
	 
	public static final int 		NO_RANK  					= 0;
	public static final int 		NO_SUIT  					= 0;
	
	public static final int 		MIN_SUIT 					= 1;
	public static final int 		MIN_RANK 					= 1;
	public static final int 		MAX_SUIT 					= 4;
	public static final int 		MAX_RANK 					= 13;
	
	// The score data.
	public static final int 		FIVE_OF_KIND 				= 9;
	public static final int 		STRAIGHT_FLUSH 				= 8;
	public static final int 		FOUR_OF_KIND 				= 7;
	public static final int 		FULL_HOUSE 					= 6;
	public static final int 		FLUSH 						= 5;
	public static final int 		STRAIGHT 					= 4;
	public static final int 		THREE_OF_KIND 				= 3;
	public static final int 		TWO_PAIRS 					= 2;
	public static final int 		ONE_PAIR 					= 1;
	public static final int 		NOTHING 					= 0;
	
	public int level = 0;
	
	/**
	 * Constructor
	 */
	public PokerScorer() 
	{
		super();
	}

	/**
	 * Compute the value of this hand.
	 *
	 * @param start
	 * @param dice
	 * @maxRank 
	 * 
	 * @return
	 */
	@Override
	public int computeHand (final int start, final List<Card> dice)
	{
		// Do we have a flush?
		boolean flush 		= false;
		boolean straight 	= false;
		boolean fiveOfKind  = false;
		boolean fourOfKind  = false;
		boolean threeOfKind	= false;
		int     pairCount   = 0;
		int     zeroCount   = 0;
		int     rolledScore = 0;
		
		// First look for a flush.
		final List<Integer> suitCounts = getSuitCount (start, dice);
		for (final Integer element : suitCounts) {
			if (element>=5) {
				flush = true;
				break;
			}
		}

		// Count how many of each dice.
		final List<Integer> rankCounts = getRankCount (start, dice, MAX_RANK);

		// Look for scores.
		// Search by how many things we have.
		boolean validStraight = true;
		for (int pos=0; pos<rankCounts.size(); pos++) {
			int rankCount = rankCounts.get(pos);
			
			switch (rankCount) {
			case 1:
				// A straight?
				if (validStraight) {
					straight = true;
				}
				break;

			case 2:
				// No straight, but pair
				straight      	= false;
				validStraight 	= false;
				pairCount++;
				break;

			case 3:
				// No straight, but three of kind.
				straight      	= false;
				validStraight 	= false;
				threeOfKind   	= true;
				break;

			case 4:
				// No straight, but four of kind.
				straight      	= false;
				validStraight 	= false;
				fourOfKind    	= true;
				break;

			case 5:
				// No straight, but five of kind.
				straight      	= false;
				validStraight 	= false;
				fiveOfKind    	= true;
				break;

			default:
				zeroCount++;
				if (zeroCount>1 || (pos>0 && pos<5)) {
					// No straight, no nothing.
					straight      	= false;
					validStraight 	= false;
				}
			}
		}

		// Any score?
		if (fiveOfKind) {
			// A five of a kind.
			rolledScore 			= FIVE_OF_KIND;
		}
		else if (straight && flush) {
			// A straight flush?
			rolledScore 			= STRAIGHT_FLUSH;
		}
		else if (fourOfKind) {
			// Four of a kind.
			rolledScore 			= FOUR_OF_KIND;
		}
		else if (threeOfKind && pairCount>0) {
			// Full house.
			rolledScore 			= FULL_HOUSE;
		}
		else if (flush) {
			// Flush.
			rolledScore 			= FLUSH;
		}
		else if (straight) {
			// Straight.
			rolledScore 			= STRAIGHT;
		}
		else if (threeOfKind) {
			// Three of a kind.
			rolledScore 			= THREE_OF_KIND;
		}
		else if (pairCount>1) {
			// Two pairs.
			rolledScore 			= TWO_PAIRS;
		}
		else if (pairCount>0) {
			// One pair.
			rolledScore 			= ONE_PAIR;
		}
		else {
			// Nothing :(
			rolledScore 			= NOTHING;
		}

		return rolledScore;
	}

	/**
	 * Determine the number of suits in the given hand.
	 * 
	 * @param dice
	 * 
	 * @return an array with the suit counts.
	 * 
	 * 	Array Index 0 = no suit's count
	 * 	Array Index 1 = heart's count
	 *  Array Index 2 = diamond's count
	 *  Array Index 3 = club's count
	 *  Array Index 4 = spade's count 
	 */
	@Override
	public List<Integer> getSuitCount (final int start, final List<Card> dice)
	{		
		// Create and initialize the suit array.
		List<Integer> suits = new ArrayList<Integer> (MAX_SUIT+1);
		for (int suit=NO_SUIT; suit<=MAX_SUIT; suit++) {
			suits.add(NO_SUIT);
		}
		
		// Go through the dice.
		for (int pos=start; pos<dice.size() && pos<start+5; pos++) {
								
			// Get the suit and rank of this dice.						
			Card card = dice.get(pos);
			
			// Get the suit.
			int suit = card.getSuit();
			
			// Increment the suit count. 
			if (suit>0) {
				suits.set(suit, suits.get(suit)+1);			
			}			
		}
		
		return suits;
	}
	
	/**
	 * Retrieve the level.
	 */
	public int getLevel ()
	{
		return level;
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
	 * 	0 = no rank's count
	 * 	1 = one's count
	 *  2 = two's count
	 *  3 = three's count
	 *  4 = four's count
	 *  5 = five's count
	 *  6 = sixes count    O_RANK
	 */
	@Override
	public List<Integer> getRankCount (final int start, final List<Card> dice, int maxRank)
	{
		// Create and initialize the rank array.
		List<Integer> ranks = new ArrayList<Integer> (maxRank+1);
		for (int rank=NO_RANK; rank<=maxRank; rank++) {
			ranks.add(NO_RANK);
		}
		
		// Go through the dice.
		for (int pos=start; pos<dice.size() && pos<start+5; pos++) {
			// Get the suit and rank of this dice.						
			Card card = dice.get(pos);
			
			// Get the rank.
			int rank = card.getRank();
			
			// Increment the rank count, suit must be greater than  0
			if (card.getSuit()>0) {
				ranks.set(rank, ranks.get(rank)+1);	
			}		
		}
		
		return ranks;
	}

	/**
	 * Return the maximum number of cards.
	 */
	@Override
	public int getMaxCards() 
	{
		return MAX_CARDS;
	}
	
	/**
	 * Return the maximum number of suits.
	 */
	@Override 
	public int getFirstSuit() 
	{
		return MIN_SUIT;
	}

	/**
	 * Return the maximum number of ranks.
	 */
	@Override
	public int getFirstRank() 
	{
		return MIN_RANK;
	}
	
	/**
	 * Return the maximum number of suits.
	 */
	@Override 
	public int getLastSuit() 
	{
		return MAX_SUIT;
	}

	/**
	 * Return the maximum number of ranks.
	 */
	@Override
	public int getLastRank() 
	{
		return MAX_RANK;
	}
	
	// //////////////////////////////////////////////////////////////////////////////
	// Don't just stand there, create something.
	// //////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Create the hand to beat.
	 */
	public List<Integer> createHandToBeat ()
	{
		ArrayList<Integer> handToBeat = new ArrayList<Integer> (MAX_CARDS);
		
		handToBeat.add(R.drawable.card_24_32_1_5);
		handToBeat.add(R.drawable.card_24_32_1_6);
		handToBeat.add(R.drawable.card_24_32_1_7);
		handToBeat.add(R.drawable.card_24_32_1_8);
		handToBeat.add(R.drawable.card_24_32_1_9);
		
		return handToBeat;
	}
	
	/**
	 * Create a new card.
	 */
	public Card createCard (Context context)
	{
		int suit = Num.rnd(getFirstSuit(), getLastSuit());
		int rank = Num.rnd(getFirstRank(), getLastRank());
		
		return createCard (context, suit, rank);
	}
	
	/**
	 * Create a new card.
	 */
	public Card createCard (Context context, int suit, int rank)
	{
		return new Card (context, suit, rank, getImageId (suit, rank));
	}
	
	/**
	 * Get the images id associated with this suit / rank.
	 * 
	 * @param resources
	 * @param diceSize
	 * @return
	 */
	public static int getImageId (int suit, int rank)
	{
		// Initialized?
		if (Helper.initialized) {
			// Got suit.
			switch (suit) {

			// Which suit?
			case Card.HEART: {
				// Load the hearts

				// Got rank?
				switch (rank) {
				case Card.ONE:
					return R.drawable.card_24_32_1_1;
				case Card.TWO:
					return R.drawable.card_24_32_1_2;
				case Card.THREE:
					return R.drawable.card_24_32_1_3;
				case Card.FOUR:
					return R.drawable.card_24_32_1_4;
				case Card.FIVE:
					return R.drawable.card_24_32_1_5;
				case Card.SIX:
					return R.drawable.card_24_32_1_6;
				case Card.SEVEN:
					return R.drawable.card_24_32_1_7;
				case Card.EIGHT:
					return R.drawable.card_24_32_1_8;
				case Card.NINE:
					return R.drawable.card_24_32_1_9;
				case Card.TEN:
					return R.drawable.card_24_32_1_10;
				case Card.JACK:
					return R.drawable.card_24_32_1_11;
				case Card.QUEEN:
					return R.drawable.card_24_32_1_12;
				case Card.KING:
					return R.drawable.card_24_32_1_13;
				case Card.ACE:
					return R.drawable.card_24_32_1_1;
				case Card.WILD:
					return R.drawable.back_24_32_1;
				default:
					return R.drawable.back_24_32_1;
				}
			}

			// Which suit?
			case Card.DIAMOND: {
				// Load a diamond.

				// Got rank?
				switch (rank) {
				case Card.ONE:
					return R.drawable.card_24_32_2_1;
				case Card.TWO:
					return R.drawable.card_24_32_2_2;
				case Card.THREE:
					return R.drawable.card_24_32_2_3;
				case Card.FOUR:
					return R.drawable.card_24_32_2_4;
				case Card.FIVE:
					return R.drawable.card_24_32_2_5;
				case Card.SIX:
					return R.drawable.card_24_32_2_6;
				case Card.SEVEN:
					return R.drawable.card_24_32_2_7;
				case Card.EIGHT:
					return R.drawable.card_24_32_2_8;
				case Card.NINE:
					return R.drawable.card_24_32_2_9;
				case Card.TEN:
					return R.drawable.card_24_32_2_10;
				case Card.JACK:
					return R.drawable.card_24_32_2_11;
				case Card.QUEEN:
					return R.drawable.card_24_32_2_12;
				case Card.KING:
					return R.drawable.card_24_32_2_13;
				case Card.ACE:
					return R.drawable.card_24_32_2_1;
				case Card.WILD:
					return R.drawable.back_24_32_2;
				default:
					return R.drawable.back_24_32_2;
				}
			}

			// Which suit?
			case Card.CLUB: {
				// Load a club

				// Got rank?
				switch (rank) {
				case Card.ONE:
					return R.drawable.card_24_32_3_1;
				case Card.TWO:
					return R.drawable.card_24_32_3_2;
				case Card.THREE:
					return R.drawable.card_24_32_3_3;
				case Card.FOUR:
					return R.drawable.card_24_32_3_4;
				case Card.FIVE:
					return R.drawable.card_24_32_3_5;
				case Card.SIX:
					return R.drawable.card_24_32_3_6;
				case Card.SEVEN:
					return R.drawable.card_24_32_3_7;
				case Card.EIGHT:
					return R.drawable.card_24_32_3_8;
				case Card.NINE:
					return R.drawable.card_24_32_3_9;
				case Card.TEN:
					return R.drawable.card_24_32_3_10;
				case Card.JACK:
					return R.drawable.card_24_32_3_11;
				case Card.QUEEN:
					return R.drawable.card_24_32_3_12;
				case Card.KING:
					return R.drawable.card_24_32_3_13;
				case Card.ACE:
					return R.drawable.card_24_32_3_1;
				case Card.WILD:
					return R.drawable.back_24_32_3;
				default:
					return R.drawable.back_24_32_3;
				}
			}

			// Which suit?
			case Card.SPADE: {
				// Load a spade.

				// Got rank?
				switch (rank) {
				case Card.ONE:
					return R.drawable.card_24_32_4_1;
				case Card.TWO:
					return R.drawable.card_24_32_4_2;
				case Card.THREE:
					return R.drawable.card_24_32_4_3;
				case Card.FOUR:
					return R.drawable.card_24_32_4_4;
				case Card.FIVE:
					return R.drawable.card_24_32_4_5;
				case Card.SIX:
					return R.drawable.card_24_32_4_6;
				case Card.SEVEN:
					return R.drawable.card_24_32_4_7;
				case Card.EIGHT:
					return R.drawable.card_24_32_4_8;
				case Card.NINE:
					return R.drawable.card_24_32_4_9;
				case Card.TEN:
					return R.drawable.card_24_32_4_10;
				case Card.JACK:
					return R.drawable.card_24_32_4_11;
				case Card.QUEEN:
					return R.drawable.card_24_32_4_12;
				case Card.KING:
					return R.drawable.card_24_32_4_13;
				case Card.ACE:
					return R.drawable.card_24_32_4_1;
				case Card.WILD:
					return R.drawable.back_24_32_4;
				default:
					return R.drawable.back_24_32_4;
				}
			  }
			}
		}

		return Helper.ZERO;
	}
}
