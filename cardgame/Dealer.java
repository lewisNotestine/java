import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**Dealer.java
 * Responsibilities:
 * ~Holds a deck to deal cards with.
 * ~Holds a set of rules specifying how cards are dealt.
 * ~Deals the cards into hands in "game" units, resetting the deck between games if there are not enough cards to play a game.
 * ~Holds formatting rules and methods for printing cards.
 * */
public class Dealer
{	
	/**The deck to be played with.
	 * */
	private Deck 		currentDeck;
	
	/**The number of hands to be played during each game dealt. 
	 * */
	private Integer		handsPerGame;  
	
	/**Format for printing cards.
	 * */
	private Integer printFormat;

	/**The number of games played since the last time the deck was set.
	 * */
	private Integer 	gamesPlayed;
	
	/**Whether or not the deck is shuffled when it's reset.
	 * */
	private Boolean 	shuffleOn;		//whether or not dealer shuffles when deck is reset by default.
	
	/**List of hands in the game.
	 * */
	private List<Hand>	currentHands; 
	
	/**Number of cards to deal to each hand.  Different than Hand.cardsPerHand field in that different hands may exist for different dealers, who are dealing different games
	 * */
	private Integer 	cardsPerHand; 	 
	
	//private static final Integer DEFAULT_LINE_CARDS = 1;
	private static final Integer DEFAULT_HANDS_PER_GAME = -1;
	private static final Integer DEFAULT_CARDS_PER_HAND = -1;
	private static final Boolean DEFAULT_SHUFFLE = false; 
	
	/**Default constructor.
	 * */
	public Dealer() {
		gamesPlayed = 0;
 		setNewRules(DEFAULT_SHUFFLE, DEFAULT_HANDS_PER_GAME, DEFAULT_CARDS_PER_HAND);
 		setDeck(DEFAULT_SHUFFLE);
 	}
	
	/**Constructor that takes the rules of the game as parameters. 
	 * */
	public Dealer(Boolean isShuffled, Integer numHands, Integer handCardLimit, Integer cardsPerLine) {
		gamesPlayed = 0;
		setNewRules(isShuffled, numHands, handCardLimit);
		setPrintFormat(cardsPerLine);
		setDeck(isShuffled);	
	}
	
	/**Gets the print format as Integer
	 * @return Integer, number of cards to print on each line.
	 * */
	public Integer getPrintFormat() {
		return printFormat;
	}
	
	/**Sets the print format.  
	 * @param newPrintFormat: The number of cards to print on each line of output. 
	 * */
	public void setPrintFormat(Integer newPrintFormat) {
		printFormat = newPrintFormat;
		return;
	}
	
	
	/**Print a group of Card objects. applies to anything that is a collection of Card objects.
	 * @param cardGroup: the collection to print.
	 * */
	public void printCardCollection(Collection<Card> cardGroup) {
		Integer cardCount = 0;
		
		for (Card eachCard : cardGroup) {
			eachCard.printCard();
			
			if (cardCount < cardGroup.size() - 1) {
				System.out.printf(", ");
			}
			
			if ((cardCount + 1) % printFormat == 0) {
				System.out.println();
			}
			
			cardCount++;
		}
		
		System.out.println();
		return;
	}
 		
	/**Clears the list of hands and deals an entire game.  Deals out hands until the right number has been dealt to the right number of hands, then 
	 * prints the hands dealt in the game. 
	 * */
	public void dealGame() {
		Boolean handsFull = false;
		Integer	fullHandCount = 0;
		Integer currentHand = 0;
		
		countCards();
		
		setHands(); 
		
		while (!handsFull) {
		
			//decide whether to deal to current hand.
			if (currentHands.get(currentHand).isFull()) {
				fullHandCount++;
			} else {
				deal(currentHands.get(currentHand));
			}
			
			//decide when to stop dealing.
			if (fullHandCount == this.handsPerGame) {
				handsFull = true;
			}
						
			//decide which hand gets the next deal.
			if (currentHand < this.handsPerGame - 1) {
				currentHand++;
			} else {
				currentHand = 0;
			}
		}
		
		//print the hands played in the game.
		for (int i = 0; i < currentHands.size(); i++) {
			System.out.println("--- Hand " + (i + 1) + " ---\n");
			printHand(currentHands.get(i));
		}
		
		gamesPlayed++;
		return;
	}
	
	/**Deals out the number of games specified.  Prints out results of dealing.
	 * @param numGames - specifies the number of games that should be dealt. 
	 */
	public void dealGames(Integer numGames) {
		int i = 0;
		String gameHdr; 
		
		countCards();
		
		for (i = 0; i < numGames; i++) {
			gameHdr = "=== Game " + (i + 1) + " ===\n";
			System.out.println(gameHdr);
			dealGame();
		}
		
		System.out.println("=== Games Complete ===");
		
		return;
	}
	
	/**Sets the deck to a new instance of a deck, and either shuffle or don't; also prints the deck in the order it'll be used.  
	 * @param shuffle: whether or not the deck is shuffled. 
	 * */
	public void setDeck(Boolean shuffle) {
		currentDeck = new Deck();
		if (shuffle) {
			shuffleDeck();
		}	
		
		gamesPlayed = 0;
		printDeck();
		return;
	}

	/**Sets the deck to the given deck (preserving current order, etc of deck given); also prints the deck in the order it'll be used. 
	 * @param newDeck: the new deck that is given. 
	 * @param shuffle: whether or not the deck is shuffled.
	 * */
	public void setDeck(Deck newDeck, Boolean shuffle) {
		currentDeck = newDeck;
		if (shuffle) {
			shuffleDeck();
		}
		
		gamesPlayed = 0;
		printDeck();
		
		return;
	}
	
	/**Resets the game rules, setting all data fields.  
	 * */
	public void setNewRules(Boolean isShuffled,  Integer numHands, Integer handCardLimit) {		
		shuffleOn = isShuffled;
		handsPerGame = numHands;
		cardsPerHand = handCardLimit;
		
		return;
	}
	
	/**Determines need to reset the deck, and resets deck if necessary.  Called from dealGame and dealGames().
	 * */
	protected void countCards() {
		Integer spentCardCount = gamesPlayed * cardsPerHand * handsPerGame;
		Integer gameCardCost = cardsPerHand * handsPerGame;
		
		//set the deck if needed.  
		if (currentDeck == null || gameCardCost < spentCardCount) { 
			setDeck(shuffleOn);	
		}
		return;
	}
	
	/**Deals a card into one hand. Picks the card from the top of the deck. 
	 * @param handToDeal the hand to receive the card from the top of the deck.
	 * */
	protected void deal(Hand handToDeal) {
		Card cardToAdd = currentDeck.getTopCard();
		handToDeal.addCard(cardToAdd);
		
		return;
	}
	
	/**creates the list of players that play the game and sets the stored count of hands.
	 * If the old list is populated it is cleared and replaced with a new one, so this is also used to reset the hands after a game.
	 * @param numHands - The number of hands dealt in each game.
	 * */
	protected void setHands() {
		currentHands = new ArrayList<Hand>(this.handsPerGame);
		
		for (int i = 0; i < this.handsPerGame; i++) {
			currentHands.add(new Hand(cardsPerHand));
		}
		
		return;
	}
	
	/**Shuffles the deck. 
	 * */
	protected void shuffleDeck() {
		currentDeck.shuffleDeck();
		return;
	}
	
    /**Print all of the cards in the deck.
     * */
    public void printDeck() {
		System.out.println("deck:");
		printCardCollection(currentDeck);
		System.out.println();
		return;
    }
	
	/**Prints the contents of the hand, computes the score. 
	 * */
	protected void printHand(Hand handToPrint) {
		printCardCollection(handToPrint);
		System.out.println("score = " + handToPrint.getHandScore() + "\n");
		return;
	}
	
}
