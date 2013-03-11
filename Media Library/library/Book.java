package library;


class Book
	extends Item
{
	/*These members aren't declared in Item.java because they're all final and all different for each subclass.*/
	public static final String MEDIA_TYPE = "Book";
	public static final String DIV_TYPE = "pages";
	public static final String CREATOR_TYPE = "author";
	
	public Book() {
		super();
	}
	
	public Book(String newTitle) {
		super(newTitle);
	}
	
	public Book(String newTitle, String newCreator, Integer newDivs, String... varargs) {
		super(newTitle, newCreator, newDivs, varargs);
		setDivType(DIV_TYPE);
		setCreatorType(CREATOR_TYPE);
		setMediaType(MEDIA_TYPE);
	}
	
	/**Overall Testing method. 
	 * */
	public static void testBook() {
		Book.testToString();
	}
	
	/**tests toString method.
	 * */
	public static void testToString() {
		Book tester = new Book("testTitle", "testAuthor", 99, "testKeyWordA", "testKeyWordB");
		System.out.print(tester.toString());
		
		Book tester2 = new Book("testTitle2", "testAuthor2", 123, "testKeyWordB", "testKeyWordA");
		System.out.print(tester2.toString());
		return;		
	}
	
	/**toString method.
	 * */
	public String toString() {
		String retVal = super.toString();
		retVal += this.getTitleAndKeyWds() + "\n";
		return retVal;
	}
	
	/**Return's the book's author.
	 * */
	public String getAuthor() {
		return getCreator();
	}
	
	/**Sets the book's author.
	 * */
	public void setAuthor(String newAuthor) {
		setCreator(newAuthor);
		return;
	}
	
	/**Returns whether the book's author is the name given in the param.
	 * */
	public Boolean hasAuthor(String authorName) {
		return hasCreator(authorName);
	}
}
