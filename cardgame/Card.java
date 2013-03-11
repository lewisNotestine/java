/**Card.java
 * Stores the information relevant to a card.  This includes enumerations for the card's rank and suit, and the score computed for the card.
 * Also has static data and methods for how a card is printed from a collection. 
 * */

public class Card
{
	public enum Rank
	{
		ACE 	(11),
		KING 	(10), 
		QUEEN	(10),
		JACK	(10),
		TEN		(10),
		NINE	(9),
		EIGHT	(8),
		SEVEN	(7), 
		SIX		(6),
		FIVE	(5),
		FOUR	(4),
		THREE	(3), 
		DEUCE	(2);
		
		private final Integer rankScore; 
		
		private Rank(Integer myScore) {
			this.rankScore = myScore;
		}
		
		private Integer rankScore() { return rankScore; }
		
		
	}
	
	public enum Suit
	{
		 HEARTS		(4),
		 DIAMONDS	(3),
		 CLUBS		(2), 
		 SPADES		(1);
		 
		 private final Integer suitScore;
		 
		 private Suit(Integer myScore) {
			 this.suitScore = myScore;
		 }
		 
		 private Integer suitScore() { return suitScore; }
	}
	
	//End enumerations
	
	/**Card's rank (2-Ace)
	 * */
	private final Rank	rank;
	
	/**Card's suit.
	 * */
	private final Suit	suit;
		
	/**Default Card constructor.  Sets the printFormat to default if it hasn't already been set by a previous (local or otherwise) call to setPrintFormat.
	 * */
	Card(Rank rank, Suit suit)
	{
	    this.rank = rank;
	    this.suit = suit;
	}
	
	
	
	/**Returns rank of card.
	 * */
	public Rank rank()          { return rank; }
	
	/**returns suit of card.
	 * */
	public Suit suit()          { return suit; }
	public String toString()    { return rank + " of " + suit; }
	
	/**displays the card's rank, suit and score in parentheses.
	 * */
	public void printCard() {
		System.out.print( this.toString() + "(" + this.getCardScore() + ")");
	}
	
	/**Returns the score for the card, which is calculated as the product of the rank score and the suit score. 
	 * */
	public Integer getCardScore() {
		return this.rank.rankScore() * this.suit.suitScore();
	}
}
