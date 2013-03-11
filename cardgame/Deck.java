import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

/**Deck.java
 * is a queue of cards that are dealt from the top.  
 * Responsibilities: 
 * Storing cards in the queue. 
 *  
 */
public class Deck extends ArrayDeque<Card> implements Queue<Card>
{

	private static final long serialVersionUID = 1L;

	private static final List<Card> protoDeck = new ArrayList<Card>();

    // initialize prototype deck
    static {
        for (Card.Suit suit : Card.Suit.values())
            for (Card.Rank rank : Card.Rank.values())
                protoDeck.add(new Card(rank, suit));
    }
    
    private Boolean		isShuffled; //Indicates whether the deck has been shuffled (i.e., is not in the same order as protoDeck). 
    
    /**Default constructor; sets the deck to be the prototype deck.
     * outputs 1 card on each line when printing. 
     * */
    public Deck() {
    	super(Deck.protoDeck);
    	isShuffled = false;  	
    } 
    
    /**Gets the card from the top of the deck.
     * */
    public Card getTopCard() {
    	return this.remove();
    }
    

    
    /**Returns whether or not the deck has been shuffled.
     * */
    public Boolean shuffled() {
    	return isShuffled;
    }
    
    /**Shuffles the deck collection.
     * */
    public void shuffleDeck() {    
    	List<Card> tempDeck = new ArrayList<Card>(Deck.protoDeck);
    	Collections.shuffle(tempDeck);
    	this.clear();
    	this.addAll(tempDeck);
    	
    	isShuffled = true;
    }
    
}
