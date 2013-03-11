// Each book, music album, or movie will be an instance of a subclass of this class.
// Instances of this class should not be created. Specifying it as 'abstract' ensures
// that they cannot.

package library;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**Item class: 
 * Serves as parent class for all media types to be used in a Library database.  has basic data for data common to media items, 
 * and methods handling the formatting of these media items for output. 
 * */

public abstract class Item implements Comparable<Item>
{
	private static final Integer 		PRINT_SPACES = 10;	//number of spaces to print in each row of the 
		
	private String						title;				//Title of the work (name of book, album or film).
	private String 						creator; 			//Name of principal Artist/Creator of work. could be a director, band, or writer.
	private Collection<String>			keyWords;			//Keywords for content items. Common to all types of database item.  Implemented as TreeSet because items are sorted alphabetically.
	private Integer						contentDivs;		//Number of content divisions.  Could be number of pages, number of tracks, etc.
	
	private String						mediaType;			//overall type of media (e.g., Book, Movie, Music Album).
	private String 						divType;			//the type of object represented by a content division (e.g., pages, tracks, scenes)
	private String						creatorType;		//Type of creator (e.g., author, director, band name).
	
	/**protected Constructors.
	 * */
	protected Item() {
		title = "";
		creator = "";
		keyWords = new TreeSet<String>();
		contentDivs = -1;
	}
	
	/**title-only ctor, used for deletions
	 * */
	protected Item(String newTitle) {
		title = newTitle;
		creator = "";
		keyWords = new TreeSet<String>();
		contentDivs = -1;
	}
	
	/**Protected constructors.
	 * */
	protected Item(String newTitle, String newCreator, Integer newDivs, String... varargs) {
		title 	= newTitle;
		creator = newCreator;
		keyWords = new TreeSet<String>(Arrays.asList(varargs));
		contentDivs = newDivs;
	}
	
	/**compare items on basis of title.
	 * */
	public int compareTo(Item otherItem) {
		return this.title.compareTo(otherItem.title);
	}
	
	/**Returns whether or not an Item contains the keyword given as param.
	 * */
	public Boolean hasKeyword(String keyWord) {
		return keyWords.contains(keyWord);
	}

	/**Returns whether the item's creator is equal to the name given in the parameter.
	 * */
	protected Boolean hasCreator(String creatorName) {
		return creator.equals(creatorName);
	}
	
	/**Returns the String representation of the object.
	 * */
	public String toString() {
		String retVal = "";
		retVal += getClassHeader() + "\n";
		retVal += getCreatorAndDivs();
		
		return retVal;
	}
	
	/**Returns the media type name.
	 * */
	public String getMediaType() {
		return mediaType;
	}
	
	/**Returns the class header for the object to be used in the toString method. 
	 * */
	protected String getClassHeader() {
		String retVal = "-" + this.mediaType + "-";
		return retVal;
	}
	
	/**Gets the title of the work.
	 * */
	protected String getTitle() {
		return title;
	}
	
	/**Sets the title of the work.
	 * */
	protected void setTitle(String newTitle) {
		title = newTitle;
	}
	
	/**Gets the creator member, otherwise inaccessible to subclasses.
	 * */
	protected String getCreator() {
		return creator;
	}
	
	/**Sets the creator member.  
	 * */
	protected void setCreator(String newCreator) {
		creator = newCreator;
	}
	
	/**Returns the collection of keywords for the item.
	 * */
	protected Collection<String> getKeyWords() {
		return keyWords;
	}
	
	/**Returns a string representation of the keywords (formatted as a comma-delimited list).
	 * */
	protected String outputKeyWords() {
		Iterator<String> 	castIter = keyWords.iterator();
		String 	 			retVal = "";
		String 	 			currentMember;
		
		while (castIter.hasNext()) {
			currentMember = castIter.next();
			retVal += currentMember;
			if (castIter.hasNext()) {
				retVal += ", ";
			} 
		}
		
		return retVal;
	}
	
	/**Replaces keyWords with parameter value.
	 * */
	protected void setKeyWords(Collection<String> newKeyWords) {
		keyWords = newKeyWords;
		return;
	}
	
	/**Adds a single keyWord string to collection.
	 * */
	protected void addKeyWord(String wordToAdd) {
		keyWords.add(wordToAdd);
		return;
	}
	
	/**Adds a collection of keyWords to the item's key words.  
	 * POST: after adding the item's keywords is the union of its previous keywords and wordsToAdd.
	 * */
	protected void addKeyWords(Collection<String> wordsToAdd) {
		keyWords.addAll(wordsToAdd);
	}
	
	/**Returns number of content Divisions.
	 * */
	protected Integer getContentDivs() {
		return contentDivs;
	}
	
	/**Sets the content divs of the item.
	 * */
	protected void setContentDivs(Integer newContentDivs) {
		contentDivs = newContentDivs;
		return;
	}
	
	/**Gets first two information pieces for toString method.
	 * */
	protected String getCreatorAndDivs() {
		String 	retVal = "";
		retVal += getEntryRow(creatorType, getCreator()) + "\n";
		retVal += getEntryRow("# " + divType, getContentDivs().toString()) + "\n";
		
		return retVal;
	}
	
	/**Gets last two information pieces for toString method.
	 * */
	protected String getTitleAndKeyWds() {
		String retVal = "";
		retVal += getEntryRow("title", getTitle()) + "\n";
		retVal += getEntryRow("keywords", outputKeyWords()) + "\n";
		
		return retVal;
	}
	
	
	
	/**Sets the media type name.
	 * */
	protected void setMediaType(String newMediaType) {
		mediaType = newMediaType;
		return;
	}
	
	/**gets the div type for the media item.
	 * */
	protected String getDivType() {
		return divType;
	}
	
	/**sets the div type for the media item.
	 * */
	protected void setDivType(String newType) {
		divType = newType;
		return;
	}
	
	/**Returns the type name of the creator (e.g., author, band, director).
	 * */
	protected String getCreatorType() {
		return creatorType;
	}
	
	/**Sets the type name of the creator (e.g., author, band, director).
	 * */
	protected void setCreatorType(String newType) {
		creatorType = newType;
		return;
	}
	
	/**formats a row of output with a header column.  The header column is 
	 * */
	protected String getEntryRow(String headerCol, String contentCol) {
		String retVal = String.format("%1$-" + PRINT_SPACES + "s", headerCol + ":" );
		retVal += contentCol;
		return retVal;
	}
}
