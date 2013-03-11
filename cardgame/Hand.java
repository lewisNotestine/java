import java.util.ArrayList;
import java.util.LinkedHashSet; 

/**Hand.java
 * Class representing a hand of cards.  Stores information about each of the cards in the set.  Cards are stored in a LinkedHashSet to preserve insertion ordering and 
 * Responsibilities:
 * Storing a set of cards, managing the local limit of cards in the hand, computing the score from each of the cards in the hand, 
 * printing the hand.  
 * */

public class Hand extends LinkedHashSet<Card>{

	private static final long serialVersionUID = 1L;
	private Integer cardsPerHand; 
	private Integer handScore;
	
	/**Default Constructor.
	 * */
	public Hand() {
		new ArrayList<Card>();
		//cardSet = new ArrayList<Card>();
		cardsPerHand = -1;
		handScore = 0;
	}
	
	/**Constructor provides number of cards per hand.
	 * @param cardLimit sets number of cards per hand. 
	 * */
	public Hand(Integer cardLimit) {
		new ArrayList<Card>();
		//cardSet = new ArrayList<Card>();
		cardsPerHand = cardLimit;
		handScore = 0;
	}
	
	/**Gets the number of cards dealt at each hand.
	 * */
	public Integer getCardLimit() {
		return cardsPerHand;
	}
	
	/**Sets the number of cards to be dealt at each hand. 
	 * */
	public void setCardLimit(Integer cardLimit) {
		cardsPerHand = cardLimit;
		return;
	}
	

	/**Shows whether or not the hand is full (has its full allotment of cards). 
	 * */
	public Boolean isFull() {
		return this.size() == cardsPerHand;
	}
	
	/**Adds a card to the hand, only if the hand has enough space left. 
	 * */
	public void addCard(Card newCard) {
		if (this.size() < cardsPerHand ) {
			this.add(newCard);
			this.handScore += newCard.getCardScore();
		}
	}
	
	/**Gets the score of hand
	 * */
	public Integer getHandScore() {		
		return this.handScore;
	}
	
}
