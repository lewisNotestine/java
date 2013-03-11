package library;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


/**Library class.
 * Handles the storage and querying of distinct media types, all of which share common characteristics (e.g., a title and an artist/creator.)  
 * Creates indices for storing these media types for quick retrieval.
 * Passes formatted media item output to a user interface.
 * */

public class Library
{
	/*Set of Index structures for managing items by lookup type.  
	 * These should increase performance for querying but decrease for additions and deletions somewhat like a SQL DB.*/
	
	private TreeMap<String, TreeSet<String>> keyWordIndices;	//stores list of keyWords (keyword name as key, list of item titles as value).  Used as lookup table. 
	private TreeMap<String, TreeSet<String>> memberIndices;		//stores list of members (i.e., cast members or band members) with member name as key and list of titles as value. used as lookup table. 
	private TreeMap<String, TreeSet<String>> artistIndices;		//stores list of artists (creators), with creator name as key and list of titles as value. 
	private TreeMap<String, TreeSet<String>> mediaIndices;		//stores list of classes ("Item" subclasses, with class type as key and list of titles as value).
	
	
	private TreeMap<String, Item> itemCollection;	//place where all the items are stored.
	
	public Library() {
		itemCollection = new TreeMap<String, Item>();
		initIndices();
		//Item.initItemTypes();
	}
	
	/** Test methods:
	 */
	public static void testBook() {
		Book.testToString();
	}
	
	public Collection<String> testMediaIndices() {
		return mediaIndices.keySet();
	}
	
	// general methods
	
	/*** returns all of the items which have the specified keyword
	 * @param keyword
	 * @return Returns the collection of items in the library having the keyword.  TODO: Fix for performance!
	 */
	public Collection<Item> itemsForKeyword(String keyword) {
		return searchIndexOne(keyWordIndices, keyword);
	}
	
	// print an item from this library to the output stream provided
	public void printItem(PrintStream out, Item item) {
		out.print(item.toString());
		return;
	}
	
	// book-related methods
	
	/**adds a book to the library.  Returns book added if successful, else returns null. 
	 * */
	public Item addBook(String title, String author, int nPages, String... keywords) {
		return addLibItem(Book.class, title, author, nPages, keywords);
	}
	
	/*** removes a book from the library.  Returns true if removal was successful, false otherwise */
	public boolean removeBook(String title) {
		return removeLibItem(title);
	}
	
	/** returns all of the books by the specified author*/
	public Collection<Item> booksByAuthor(String author) {
		return searchIndexTwo(mediaIndices, Book.class.toString(), artistIndices, author);
	}
	
	/** returns all of the books in the library. 
	 *  TODO: make a private method for returning each item type (book, movie, album) and call it in methods like these.
	 *  */
	public Collection<Item> books() {
		return searchIndexOne(mediaIndices, Book.class.toString());
	}
	
	// music-related methods
	
	// adds a music album to the library
	public Item addMusicAlbum(String title, String band, int nSongs, String... keywords) {
		return addLibItem(MusicAlbum.class, title, band, nSongs, keywords);
	}

	/** adds the specified band members to a music album.  Can add multiple members using varargs.
	 * */
	public void addBandMembers(Item album, String... members) {
		((MusicAlbum) album).addBandMembers(members);
		addMemberIndex((MusicAlbum) album);
		return;
	}
	
	// removes a music album from the library
	public boolean removeMusicAlbum(String title) {
		return removeLibItem(title);
	}

	// returns all of the music albums by the specified band
	public Collection<Item> musicByBand(String band) {	
		return searchIndexTwo(mediaIndices, MusicAlbum.class.toString(), artistIndices, band);
	}
	
	// returns all of the music albums by the specified musician
	public Collection<Item> musicByMusician(String musician) {
		return searchIndexTwo(mediaIndices, MusicAlbum.class.toString(), memberIndices, musician);
	}
	
	// returns all of the music albums in the library
	public Collection<Item> musicAlbums() {
		return searchIndexOne(mediaIndices, MusicAlbum.class.toString());
	}
	
	// movie-related methods
	
	/** adds a movie to the library
	 * */
	public Item addMovie(String title, String director, int nScenes, String... keywords) {
		return addLibItem(Movie.class, title, director, nScenes, keywords);
	}

	/** adds the specified actors to a movie. can add multiple items using varargs. 
	 * Also handles adding member indices.
	 * */
	public void addCast(Item movie, String... members) {
		((Movie) movie).addCast(members);
		addMemberIndex((Movie) movie);
	}

	// removes a movie from the library
	public boolean removeMovie(String title) {
		return removeLibItem(title);
	}
	
	// returns all of the movies by the specified director
	public Collection<Item> moviesByDirector(String director) {
		return searchIndexTwo(mediaIndices, Movie.class.toString(), artistIndices, director);
	}
	
	// returns all of the movies by the specified actor
	public Collection<Item> moviesByActor(String actor) {
		return searchIndexTwo(mediaIndices, Movie.class.toString(), memberIndices, actor);
	}
	
	// returns all of the movies in the library
	public Collection<Item> movies() {
		return searchIndexOne(mediaIndices, Movie.class.toString());
	}
	
	
	//PRIVATE METHODS.
	
	
	/**Performs search on one index, with one key.
	 * */
	private Collection<Item> searchIndexOne(TreeMap<String, TreeSet<String>> searchIndex, String searchKey) {
		try { 
			Collection<String> 	idx1Set = new TreeSet<String>(searchIndex.get(searchKey));
			Collection<Item> 	retVal = new TreeSet<Item>();
			
			for (String k : idx1Set) {
				retVal.add(itemCollection.get(k));
			}
			
			return retVal; 
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**Performs search on two indices with two keys.
	 * */
	private Collection<Item> searchIndexTwo(TreeMap<String, TreeSet<String>> searchIndex1,  String searchKey1, TreeMap<String, TreeSet<String>> searchIndex2,  String searchKey2) {
		try {
			Collection<Item> retVal = new TreeSet<Item>();
			Collection<String> idx1Set = new TreeSet<String>(searchIndex1.get(searchKey1));
			Collection<String> idx2Set = new TreeSet<String>(searchIndex2.get(searchKey2));
	
			idx1Set.retainAll(idx2Set);
			
			for (String k : idx1Set) {
				retVal.add(itemCollection.get(k));
			}
			
			return retVal;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**Initializes indices.
	 * */
	private void initIndices() {
		keyWordIndices = new TreeMap<String, TreeSet<String>>();
		memberIndices = new TreeMap<String, TreeSet<String>>();
		artistIndices = new TreeMap<String, TreeSet<String>>();
		mediaIndices = new TreeMap<String, TreeSet<String>>();
		
		return;
	}
	
	
	/**Adds an item to a keyword index.    
	 * */
	private void addLookupIndex(TreeMap<String, TreeSet<String>> placeToAdd, String itemTitle, Collection<String> inputList) {
		Iterator<String> 	inputIter = inputList.iterator();
		String 				currentInput;
		
		while (inputIter.hasNext()) {
			currentInput = inputIter.next();
			
			//if key isn't there then add it.
			if (!placeToAdd.containsKey(currentInput)) {
				placeToAdd.put(currentInput, new TreeSet<String>());
			} 
			
			//add the value for the current key word. 
			placeToAdd.get(currentInput).add(itemTitle); 
		}
		
		return;
	}
	
	/**Adds an item to a keyword index.    
	 * */
	private void addKeyWordIndex(Item newItem) {
		addLookupIndex(keyWordIndices, newItem.getTitle(), newItem.getKeyWords());
		return;
	}
	
	/**Adds an item to the member index for a music album. 
	 * */
	private void addMemberIndex(MusicAlbum newAlbum) {
		addLookupIndex(memberIndices, newAlbum.getTitle(), newAlbum.getBandMembers());
		return;
	}
	
	/**Adds an item to the member index for a movie.
	 * */
	private void addMemberIndex(Movie newMovie) {
		addLookupIndex(memberIndices, newMovie.getTitle(), newMovie.getCast());
		return;
	}
	
	/**Adds an item's title to the media index. Used for any type of Item.  
	 * */
	private void addMediaIndex(Item newItem) {
		if (!(mediaIndices.containsKey(newItem.getClass().toString()))) {
			mediaIndices.put(newItem.getClass().toString(), new TreeSet<String>());
		} 
		
		mediaIndices.get(newItem.getClass().toString()).add(newItem.getTitle());
		
		return;
	}
	
	/**Adds an item to the artistIndex.
	 * */
	private void addArtistIndex(Item newItem) {
		if (!(artistIndices.containsKey(newItem.getCreator()))) {
			artistIndices.put(newItem.getCreator(), new TreeSet<String>());
		}
		
		artistIndices.get(newItem.getCreator()).add(newItem.getTitle());
		
		return;
	}
	
	/**Adds a generic Item object to the library. Uses reflection to dynamically cast.
	 * */
	private Item addLibItem(Class<? extends Item> itemType, String title, String author, int nPages, String... keywords) {
		try {
			Constructor<?> cons =  itemType.getConstructor(String.class, String.class, Integer.class, String[].class );
			
			Item newItem = itemType.cast(cons.newInstance(title, author, nPages, keywords));  
			
			itemCollection.put(title, newItem);
			addKeyWordIndex(newItem);
			addMediaIndex(newItem);
			addArtistIndex(newItem);
			
			return newItem;
		} catch(Exception ex) {
			return null;
		}
	}
	
	
	/**Removes a library item from the collection, and also removes the title from all the cross-reference indices.
	 * */
	private boolean removeLibItem(String title) {
		try {
			Item removedItem = itemCollection.remove(title);
			
			if (removedItem == null) {
				return false;
			} else {
				//put index removal stuff here. 
				removeIndices(removedItem);
				return true;
			}
		} catch(Exception ex) {
			return false;
		}
	}
	
	/**removes a title from the indices of the library.  calls individual removeIndex functions.
	 * */
	private Boolean removeIndices(Item outItem) {
		Boolean removeSuccess = false;
		removeSuccess = removeLibraryIndex(artistIndices, outItem);
		removeSuccess = removeLibraryIndex(memberIndices, outItem);
		removeSuccess = removeLibraryIndex(keyWordIndices, outItem);
		removeSuccess = removeLibraryIndex(mediaIndices, outItem);
		return removeSuccess;
	}
	
	/**general index removal method.
	 * */
	private Boolean removeLibraryIndex(TreeMap<String, TreeSet<String>> libIndex, Item outItem) {
		Boolean removeSuccess = true;
		
		for (Map.Entry<String, TreeSet<String>> k : libIndex.entrySet()) {
			try {
				removeSuccess = k.getValue().remove(outItem.getTitle());
			} catch (Exception ex) {
				removeSuccess = false;
			}
		} 
		
		removeSuccess = true;
		
		// Don't return false because you don't have to find the key for this to work.
		return removeSuccess;
	}
}
