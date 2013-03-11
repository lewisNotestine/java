package library;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**MusicAlbum class.  Extends Item, includes bandMembers field.
 * */
class MusicAlbum
	extends Item
{
	/*These members aren't declared in Item.java because they're all final and all different for each subclass.*/
	private static final String MEDIA_TYPE = "Music Album";
	private static final String DIV_TYPE = "songs";
	private static final String CREATOR_TYPE = "band";
	private static final String MEMBERS = "members";
	
	private Collection<String> bandMembers;

	/**Default ctor.
	 * */
	public MusicAlbum() {
		super();
		initMbrs();
	}
	
	public MusicAlbum(String newTitle) {
		super(newTitle);
		initMbrs();
	}
	
	
	public MusicAlbum(String newTitle, String newCreator, Integer newDivs, String... varargs) {
		super(newTitle, newCreator, newDivs, varargs);
		initMbrs();
	}
	
	/**toString method.
	 * */
	public String toString() {
		String retVal = super.toString();
		retVal += this.printMbrs() + "\n";
		retVal += this.getTitleAndKeyWds() + "\n";
		return retVal;
	}
	
	/**Returns the media type name.
	 * */
	public String getMediaType() {
		return "MusicAlbum";
	}
	
	/**Return's the book's author.
	 * */
	public String getBand() {
		return getCreator();
	}
	
	/**Sets the book's author.
	 * */
	public void setBand(String newBand) {
		setCreator(newBand);
		return;
	}
	
	/**Adds band members.
	 * TODO: Should this be put in item? Maybe, maybe not; it isn't implemented in Book... 
	 * */
	public void addBandMembers(String... bandMbrs) {
		bandMembers.addAll(Arrays.asList(bandMbrs));
		return;
	}
	
	/**Returns whether the album has the particular band member
	 * */
	public Boolean hasBandMember(String memberName) {
		return bandMembers.contains(memberName);
	}
	
	/**Returns collection of band members.
	 * */
	public Collection<String> getBandMembers() {
		return this.bandMembers;
	}
	
	/**Initialize specific data members.
	 * */
	private void initMbrs() {
		setDivType(DIV_TYPE);
		setCreatorType(CREATOR_TYPE);
		setMediaType(MEDIA_TYPE);
		bandMembers = new TreeSet<String>();
		
		return;
	}
	
	/**Returns a string representation for the cast members.
	 * */
	private String printMbrs() {
		Iterator<String> mbrsIter = bandMembers.iterator();
		String 	 retVal = "";
		String 	 currentMember;
		
		while (mbrsIter.hasNext()) {
			currentMember = mbrsIter.next();
			retVal += currentMember;
			if (mbrsIter.hasNext()) {
				retVal += ", "; 
			}
		}
		
		retVal = getEntryRow(MEMBERS, retVal);
		
		return retVal;
	}
	
	
}
