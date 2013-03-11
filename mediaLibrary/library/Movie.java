package library;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**Class movie. extends Item, includes 
 * */
class Movie
	extends Item
{
	/*These members aren't declared in Item.java because they're all final and all different for each subclass.*/
	public static final String MEDIA_TYPE = "Movie";
	public static final String DIV_TYPE = "scenes";
	public static final String CREATOR_TYPE = "director";
	private static final String CAST_TYPE = "cast";
	
	private Collection<String> cast;

	/**Default ctor.
	 * */
	public Movie() {
		super();
		cast = new TreeSet<String>();
	}
	
	/**title-only ctor, used in remove functions.
	 * */
	public Movie(String newTitle) {
		super(newTitle);
		cast = new TreeSet<String>();
	}
	
	/**Constructor.  Cast cannot be part of the ctor because only one set of varargs.
	 * */
	public Movie(String newTitle, String newDirector, Integer newDivs, String... varargs) { 
		super(newTitle, newDirector, newDivs, varargs);
		cast = new TreeSet<String>();
		setMediaType(MEDIA_TYPE);
		setCreatorType(CREATOR_TYPE);
		setDivType(DIV_TYPE);
	} 
	
	/**toString method.  Calls the super tostring method. 
	 * */
	public String toString() {
		String retVal = super.toString();
		retVal += printCast() + "\n";
		retVal += getTitleAndKeyWds() + "\n";
		return retVal;
	}
	
	
	/**Returns the cast members. 
	 * */
	public Collection<String> getCast() {
		return cast;
	} 
	
	/**Adds cast members to the current movie.
	 * */
	public void addCast(String... castArgs) {
		cast.addAll(Arrays.asList(castArgs));
		return;
	}
	
	/**Returns whether or not the movie has the actor given by name in it.
	 * */
	public boolean hasActor(String actorName) {
		return cast.contains(actorName);
	}
	
	/**Returns whether or not the film is directed by a particular director.
	 * */
	public boolean hasDirector(String directorName) {
		return hasCreator(directorName);
	}
	
	/**Returns a string representation for the cast members.
	 * */
	private String printCast() {
		Iterator<String> mbrsIter = cast.iterator();
		String 	 retVal = "";
		String 	 currentMember;
		
		while (mbrsIter.hasNext()) {
			currentMember = mbrsIter.next();
			retVal += currentMember;
			if (mbrsIter.hasNext()) {
				retVal += ", "; 
			}
		}
		
		retVal = getEntryRow(CAST_TYPE, retVal);
		
		return retVal;
	}
	
	
}
