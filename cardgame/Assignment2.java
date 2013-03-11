/**Assignment2.java
 *  CS 161 - Assignment 2.  Represents a testing environment for a "Dealer" class as well as Hand, Deck and Card classes representing a card-playing system.  
 * Author: 
 * 	Lewis Notestine
 * Date:
 * 	10-28-2012
 * References:
 * 	On serialization:
 * 	http://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
 * */

public class Assignment2
{
	// these can't be final, because main would not be able to set them if they were
	static int		N_GAMES;
	static int		N_HANDS;
	static int		CARDS_PER_HAND;
	static int		CARDS_PER_LINE;
    static boolean	SHUFFLE;
	
    public static void main(String args[])
    {
    	// remember to have this print your own name instead of I. Forgot
        System.out.printf("CS261 - Assignment 2 - Lewis Notestine%n%n");
        
        // get command line arguments
        if (args.length != 5) {
        	System.out.println("requires 5 arguments: nGames, nHands, cardsPerHand, cardsPerLine, shuffle");
        	return;
        	}
        try {
        	N_GAMES = Integer.parseInt(args[0]);
        	N_HANDS = Integer.parseInt(args[1]);
		    CARDS_PER_HAND = Integer.parseInt(args[2]);
		    CARDS_PER_LINE = Integer.parseInt(args[3]);
        	}
        catch (NumberFormatException e) {
        	System.out.printf("bad numeric argument %s%n", e.getMessage());
        	return;
        	}
        SHUFFLE = new Boolean(args[4]);

        System.out.printf("games: %d, hands: %d, cards per hand: %d,%ncards per line: %d, shuffle: %b%n%n",
        				  N_GAMES, N_HANDS, CARDS_PER_HAND, CARDS_PER_LINE, SHUFFLE);
        
        // add your code here
       
        Dealer myDealer = new Dealer(SHUFFLE, N_HANDS, CARDS_PER_HAND, CARDS_PER_LINE); 
        myDealer.dealGames(N_GAMES);
    }
}
