package classes;

public class LibraryObjects {
	private String title;
	private String author;
	private String publisher;
	private String mediaType;
	private int qtyAvailable;
	private int qtyBorrowed;
	private int libid;
	
	
	public LibraryObjects()
	{
		this.title = "";
		this.author = "";
		this.publisher = "";
		this.mediaType = "";
		this.qtyAvailable = 0;
		this.qtyBorrowed = 0;
		this.libid = -1;
	}
	
	public LibraryObjects(String title, String author, String publisher, String mediaType, int qtyAvailable,
			int qtyBorrowed, int libid) {

		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.mediaType = mediaType;
		this.qtyAvailable = qtyAvailable;
		this.qtyBorrowed = qtyBorrowed;
		this.libid = libid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public int getQtyAvailable() {
		return qtyAvailable;
	}
	public void setQtyAvailable(int qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}
	public int getQtyBorrowed() {
		return qtyBorrowed;
	}
	public void setQtyBorrowed(int qtyBorrowed) {
		this.qtyBorrowed = qtyBorrowed;
	}
	public int getLibid() {
		return libid;
	}
	public void setLibid(int libid) {
		this.libid = libid;
	}
	
}
